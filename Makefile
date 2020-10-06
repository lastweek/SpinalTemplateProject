all:
	mkdir -p generated_rtl
	sbt "runMain testPackage.top_module"

clean:
	rm -rf target/
	rm -rf project/
	rm -rf *.log
