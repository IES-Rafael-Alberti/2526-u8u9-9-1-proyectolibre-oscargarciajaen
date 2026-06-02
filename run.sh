#!/bin/bash
set -e

DIR="$(cd "$(dirname "$0")" && pwd)"
JDK21="/home/mago_pop/.jdks/temurin-21.0.11"

echo "=== Compilando con Gradle ==="
"$DIR/gradlew" build --no-daemon -q

echo "=== Ejecutando aplicación ==="
CP="$DIR/build/classes/kotlin/main"

while IFS= read -r jar; do
    CP="$CP:$jar"
done < <(find /home/mago_pop/.gradle/caches/modules-2/files-2.1 \
    -name "*.jar" ! -name "*sources*" ! -name "*javadoc*" ! -name "*kotlin-test*" ! -name "*dokka*" 2>/dev/null)

exec "$JDK21/bin/java" -cp "$CP" MainKt
