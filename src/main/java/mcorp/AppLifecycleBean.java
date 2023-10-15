package mcorp;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import io.quarkus.logging.Log;

@ApplicationScoped
public class AppLifecycleBean {
    void onStart(@Observes StartupEvent ev) {
        Log.info("The application is starting, Lord...");
    }

    void onStop(@Observes ShutdownEvent ev) {
        Log.info("The application is stopping, Lord...");
    }

}