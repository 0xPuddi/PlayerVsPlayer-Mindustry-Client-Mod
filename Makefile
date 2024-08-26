gbuild:
	@./gradlew jar

grefresh:
	@./gradlew --refresh-dependencies

gcheck:
	@./gradlew check

dev:
	@./scripts/development.sh