package co.com.ingeniods.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import co.com.ingeniods.shared.modules.domain.DomainEnum;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;

@AnalyzeClasses(packages = "co.com.ingeniods", importOptions = { ImportOption.DoNotIncludeTests.class })
public class DomainLayer {

	@ArchTest
	public static final ArchRule domainAccess = classes().that().resideInAPackage("..domain.service..").should()
			.onlyBeAccessed()
			.byAnyPackage("..infrastructure..", "..application.service..", "..application.service.arguments..");

	@ArchTest
	public static final ArchRule domainModelAccess = classes().that().resideInAPackage("..domain.service..").should()
			.onlyDependOnClassesThat().resideInAnyPackage("..domain.model..", "java.lang", "java.util");

	@ArchTest
	public static final ArchRule domainEnums = classes().that().resideInAPackage("..domain.service..").and().areEnums()
			.should().be(DomainEnum.class);

	@ArchTest
	public static final ArchRule domainService = classes().that().resideInAPackage("..domain.service..").should()
			.beAnnotatedWith(FunctionalInterface.class);

}