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
    log.info("     _______. _______ .______     ____    ____  __    ______  _______");
    log.info("    /       ||   ____||   _  \\    \\   \\  /   / |  |  /      ||   ____|");
    log.info("   |   (----`|  |__   |  |_)  |    \\   \\/   /  |  | |  ,----'|  |__");
    log.info("    \\   \\    |   __|  |      /      \\      /   |  | |  |     |   __|");
    log.info(".----)   |   |  |____ |  |\\  \\----.  \\    /    |  | |  `----.|  |____");
    log.info("|_______/    |_______|| _| `._____|   \\__/     |__|  \\______||_______|");
    log.info(".______   .______       _______  _______   _______  _______  __  .__   __.  _______  _______");
    log.info("|   _  \\  |   _  \\     |   ____||       \\ |   ____||   ____||  | |  \\ |  | |   ____||       \\");
    log.info("|  |_)  | |  |_)  |    |  |__   |  .--.  ||  |__   |  |__   |  | |   \\|  | |  |__   |  .--.  |");
    log.info("|   ___/  |      /     |   __|  |  |  |  ||   __|  |   __|  |  | |  . `  | |   __|  |  |  |  |");
    log.info("|  |      |  |\\  \\----.|  |____ |  '--'  ||  |____ |  |     |  | |  |\\   | |  |____ |  '--'  |");
    log.info("| _|      | _| `._____||_______||_______/ |_______||__|     |__| |__| \\__| |_______||_______/");
    log.info("-------------------------------------------------------------------");
  }

}
