package run.freshr;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.utils.StringUtil;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.enumerations.AccountStatus;
import run.freshr.domain.auth.enumerations.Privilege;
import run.freshr.domain.auth.unit.jpa.AccountAuthUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class Runner implements ApplicationRunner {

  private final AccountAuthUnit accountAuthUnit;
  private final PasswordEncoder passwordEncoder;

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
    log.info("     ___      __    __  .___________. __    __");
    log.info("    /   \\    |  |  |  | |           ||  |  |  |");
    log.info("   /  ^  \\   |  |  |  | `---|  |----`|  |__|  |");
    log.info("  /  /_\\  \\  |  |  |  |     |  |     |   __   |");
    log.info(" /  _____  \\ |  `--'  |     |  |     |  |  |  |");
    log.info("/__/     \\__\\ \\______/      |__|     |__|  |__|");
    log.info("-------------------------------------------------------------------");

    accountAuthUnit.create(Account
        .builder()
        .privilege(Privilege.MANAGER_MAJOR)
        .uuid(StringUtil.uuidWithoutHyphen())
        .username("manager")
        .password(passwordEncoder.encode("1234"))
        .nickname("Manager")
        .introduce("I AM MIGHTY")
        .gender(Gender.OTHERS)
        .birth(LocalDate.of(2023, 12, 31))
        .build());
  }

}
