FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app
 
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
COPY banobras-bitcen-common-business banobras-bitcen-common-business
COPY banobras-bitcen-token-business banobras-bitcen-token-business
COPY banobras-bitcen-usuarios-business banobras-bitcen-usuarios-business
COPY banobras-bitcen-accesos-business banobras-bitcen-accesos-business
COPY banobras-bitcen-operaciones-business banobras-bitcen-operaciones-business
COPY banobras-bitcen-catalogos-business banobras-bitcen-catalogos-business
COPY banobras-bitcen-main-business banobras-bitcen-main-business
COPY ./certs/* .
 
RUN dos2unix mvnw
RUN chmod +x ./mvnw
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../../banobras-bitcen-main-business/target/*.jar)
 
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
ARG CERTS=/workspace/app
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build ${CERTS}/JKS* /etc/ca-certificates

EXPOSE 8083

ENTRYPOINT ["java","-cp","app:app/lib/*","mx.gob.banobras.bitcen.BitcenMainApp"]
