package run.freshr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import run.freshr.common.utils.CryptoUtil;

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
    log.info("     ___       ______   ______   ______    __    __  .__   __. .___________.");
    log.info("    /   \\     /      | /      | /  __  \\  |  |  |  | |  \\ |  | |           |");
    log.info("   /  ^  \\   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \\|  | `---|  |----`");
    log.info("  /  /_\\  \\  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |");
    log.info(" /  _____  \\ |  `----.|  `----.|  `--'  | |  `--'  | |  |\\   |     |  |");
    log.info("/__/     \\__\\ \\______| \\______| \\______/   \\______/  |__| \\__|     |__|");
    log.info("-------------------------------------------------------------------");

    log.error(CryptoUtil.encryptAes256("test.rocket.chat.username")); // sMyWN1tGcUfPD5m4OJ1/IxneiWTwKnFjGSWEthjKc87eVnOBkNijXzim6oxLD0vEu2jBejovRAt8KGeDBYI/t7S6gi4=
    log.error(CryptoUtil.encryptAes256("test.rocket.chat.password")); // 9RGsbUobBkCzST5jBD/ve9QOGr1RWd9NlRquHE6c8X3X7ETNtCqv5+CVv4MLQcMJ4bjzYdTacuO+W/2U6RFgXlJd3bM=
    log.error(CryptoUtil.encryptAes256("local.rocket.chat.username")); // BWUV+M/k4HLP0IcWmlyEB+ZUHNgAlHnUBtLeStm7lzqnyiOT43Irl5voWGW4HBnpUFGp/hTc49VPW7JJC+hIHopoZSI=
    log.error(CryptoUtil.encryptAes256("local.rocket.chat.password")); // GX5GnMjhunWangCaJwYvvbpg0Blr+dv1QD2iRvxYoCHw0gyPe/zdnaY0/e1ucQa5RuslhhP/ys7t0zjzJ0dimvzDxwc=
    log.error(CryptoUtil.encryptAes256("dev.rocket.chat.username")); // MsOI4xSjr9mP3BQHwFufa02AbnXW4pp2iHoj3SNCjWl/5EX+rkjMA8rSEDCRFZAXx2J+YB2JAZdb8deIMjt8u0VNmFE=
    log.error(CryptoUtil.encryptAes256("dev.rocket.chat.password")); // OWtkLL6fMGP4MKEcj/hB+US5lW/gbolhGbm0Z5wgWMiC39Q1TFR9ZIES5qOd8T4nD1fO8cjxDOSuF88H+vy0rTBuLdU=
    log.error(CryptoUtil.encryptAes256("staging.rocket.chat.username")); // whNV2JBraJVriE0MuBv8+XFJs6fOVTQJo/Tp4Voad1okZHGRPttUPdWPr8aoycb53k0tvq9BDxV2rNFLH5pD20Np6SQ=
    log.error(CryptoUtil.encryptAes256("staging.rocket.chat.password")); // b/aVcpVhB/5Y7f9fryXDnHDPfdlzVZ9ZVkOiIxlBoKNCQVMRFbWls2j0JgivkzAKszuzYu7rYoqupcYtUw262S85+tk=
    log.error(CryptoUtil.encryptAes256("rocket.chat.username")); // yKrjKaoPhJFx9hnuxyZEP0RE/ScTRDMmnBvurhzvoJEIoYN50DlQEyGQjvuTQAjESyXt4gk3kNBNlgd6rWQh1NoggpE=
    log.error(CryptoUtil.encryptAes256("rocket.chat.password")); // eZ9coFNx727x+dHIi74EKB1wYiHAakH1BvgTBswcgSUE6mGh1+G7aUKpIbw3PPMZlQX9y0Go5ovQUuffHpuNbwTDAf4=
  }

}
