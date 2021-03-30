package co.com.ingeniods.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;

@AnalyzeClasses(packages = "co.com.ingeniods", importOptions = { ImportOption.DoNotIncludeTests.class })
public class ApplicationLayer {

	@ArchTest
	public static final ArchRule applicationLayerAccess = classes().that().resideInAPackage("..application.service..")
			.should().onlyBeAccessed().byAnyPackage("..infrastructure..", "..application.service..");

	@ArchTest
	public static final ArchRule infrastructureLayerAccess = classes().that()
	.resideInAPackage("..application.service.arguments..")
			.should().onlyBeAccessed().byAnyPackage("..application.service..","..infrastructure..adapter..");

	

}