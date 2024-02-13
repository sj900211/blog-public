package run.freshr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Runner implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) {
    log.info("-------------------------------------------------------------------");
    log.info(" _______ .______       _______     _______. __    __  .______");
    log.info("|   ____||   _  \\     |   ____|   /       ||  |  |  | |   _  \\");
    log.info("|  |__   |  |_)  |    |  |__     |   (----`|  |__|  | |  |_)  |");
    log.info("|   __|  |      /     |   __|     \\   \\    |   __   | |      /");
    log.info("|  |     |  |\\  \\----.|  |____.----)   |   |  |  |  | |  |\\  \\----.");
    log.info("|__|     | _| `._____||_______|_______/    |__|  |__| | _| `._____|");
    log.info(" __  .__   __.  _______ .______          ___");
    log.info("|  | |  \\ |  | |   ____||   _  \\        /   \\");
    log.info("|  | |   \\|  | |  |__   |  |_)  |      /  ^  \\");
    log.info("|  | |  . `  | |   __|  |      /      /  /_\\  \\");
    log.info("|  | |  |\\   | |  |     |  |\\  \\----./  _____  \\");
    log.info("|__| |__| \\__| |__|     | _| `._____/__/     \\__\\");
    log.info(" _______   __       _______.  ______   ______   ____    ____  _______ .______     ____    ____");
    log.info("|       \\ |  |     /       | /      | /  __  \\  \\   \\  /   / |   ____||   _  \\    \\   \\  /   /");
    log.info("|  .--.  ||  |    |   (----`|  ,----'|  |  |  |  \\   \\/   /  |  |__   |  |_)  |    \\   \\/   /");
    log.info("|  |  |  ||  |     \\   \\    |  |     |  |  |  |   \\      /   |   __|  |      /      \\_    _/");
    log.info("|  '--'  ||  | .----)   |   |  `----.|  `--'  |    \\    /    |  |____ |  |\\  \\----.   |  |");
    log.info("|_______/ |__| |_______/     \\______| \\______/      \\__/     |_______|| _| `._____|   |__|");
    log.info("-------------------------------------------------------------------");
  }

}
