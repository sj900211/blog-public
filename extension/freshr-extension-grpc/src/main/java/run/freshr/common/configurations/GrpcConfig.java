package run.freshr.common.configurations;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * GRPC 설정
 *
 * @author 류성재
 * @apiNote spring boot 2 버전에서는 이 설정이 필요가 없었으나<br>
 *          3 버전 부터 GRPC 설정을 import 하지 못해서 오류가 발생하는 것을 확인.<br>
 *          3 버전에서 문제없이 개선이 된다면 해당 클래스는 삭제 처리할 예정
 * @since 2023. 6. 22. 오전 9:45:00
 */
@Configuration
@ImportAutoConfiguration({
        net.devh.boot.grpc.client.autoconfigure.GrpcClientAutoConfiguration.class,
        net.devh.boot.grpc.client.autoconfigure.GrpcClientMetricAutoConfiguration.class,
        net.devh.boot.grpc.client.autoconfigure.GrpcClientHealthAutoConfiguration.class,
        net.devh.boot.grpc.client.autoconfigure.GrpcClientSecurityAutoConfiguration.class,
        net.devh.boot.grpc.client.autoconfigure.GrpcClientTraceAutoConfiguration.class,
        net.devh.boot.grpc.client.autoconfigure.GrpcDiscoveryClientAutoConfiguration.class,

        net.devh.boot.grpc.common.autoconfigure.GrpcCommonCodecAutoConfiguration.class,
        net.devh.boot.grpc.common.autoconfigure.GrpcCommonTraceAutoConfiguration.class,

        net.devh.boot.grpc.server.autoconfigure.GrpcAdviceAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcHealthServiceAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcMetadataConsulConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcMetadataEurekaConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcMetadataNacosConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcMetadataZookeeperConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcReflectionServiceAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcServerMetricAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcServerSecurityAutoConfiguration.class,
        net.devh.boot.grpc.server.autoconfigure.GrpcServerTraceAutoConfiguration.class
})
public class GrpcConfig {
}
