all:
	mkdir -p generated_rtl
	sbt "runMain testPackage.top_module"
