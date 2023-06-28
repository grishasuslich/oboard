package com.example.demo.service;

import com.example.demo.api.dto.response.JokeDto;
import com.example.demo.model.integration.JokeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class JokeServiceImpl implements JokeService {
    private final JokeServiceAdapter adapter;
    private final Executor jokesThreadPoolTaskExecutor;
    private final AtomicLong requestId = new AtomicLong(0);

    private Map<Long, List<CompletableFuture<List<JokeResponse>>>> jokes = new ConcurrentHashMap<>();

    @Override
    public List<JokeDto> getJokes(Integer count) {
        log.info("Count in: " + count);
        long id = requestId.incrementAndGet();
        jokes.put(id, new ArrayList<>());
        int initialCount = 0;
        do {
            initialCount += 10;
            if (initialCount > count) {
                if (initialCount - count < 10) {
                    runJob(id, count - (initialCount - 10));
                }
            } else {
                runJob(id, 10);
            }
        } while (count >= initialCount);

        List<JokeDto> list = jokes.get(id).stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .map(joke -> new JokeDto()
                        .setId(joke.getId())
                        .setType(joke.getType())
                        .setSetup(joke.getSetup())
                        .setPunchline(joke.getPunchline()))
                .toList();
        jokes.remove(id);
        return list;
    }

    private void runJob(long id, int count) {
        CompletableFuture<List<JokeResponse>> childTask = CompletableFuture.supplyAsync(() -> getBatchJokes(count), jokesThreadPoolTaskExecutor);
        jokes.computeIfPresent(id, (aLong, completableFutures) -> {
            completableFutures.add(childTask);
            return completableFutures;
        });
    }

    public List<JokeResponse> getBatchJokes(Integer count) {
        log.info("start " + count);
        List<JokeResponse> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            JokeResponse joke = adapter.getJoke();
            result.add(joke);
        }
        return result;
    }
}
