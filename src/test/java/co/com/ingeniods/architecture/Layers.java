package co.com.ingeniods.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;

@AnalyzeClasses(packages = "co.com.ingeniods", importOptions = { ImportOption.DoNotIncludeTests.class })
public class Layers {

	@ArchTest
	public static final ArchRule layerAccess = layeredArchitecture()
	.layer("Config").definedBy("..config..")
	.layer("Infrastructure").definedBy("..infrastructure..")
	.layer("Domain").definedBy("..domain..")
	.layer("Application").definedBy("..application..", "..shared.event..")
	.whereLayer("Config").mayNotBeAccessedByAnyLayer()
	.whereLayer("Infrastructure").mayOnlyBeAccessedByLayers("Config")
	.whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Infrastructure", "Config")
	.whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure", "Config");

}