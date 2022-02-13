package B208.mag_jang.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class AsyncService {
    @Async
    public ListenableFuture<String> sleep(String roomId) throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("async!!!");
        return new AsyncResult<>(roomId);
    }
}
