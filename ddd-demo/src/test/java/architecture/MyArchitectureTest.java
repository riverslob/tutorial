package architecture;//package com.meal.mechant;

import com.tngtech.archunit.base.ArchUnitException;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;
import java.nio.file.Paths;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.plantuml.PlantUmlArchCondition.Configurations.consideringAllDependencies;
import static com.tngtech.archunit.library.plantuml.PlantUmlArchCondition.Configurations.consideringOnlyDependenciesInDiagram;
import static com.tngtech.archunit.library.plantuml.PlantUmlArchCondition.adhereToPlantUmlDiagram;

//@ExtendWith(ArchUnitRunner.class)
/*@AnalyzeClasses(packages = "com.meal.mechant",importOptions = {ImportOption.DoNotIncludeTests.class})
public class MyArchitectureTest {
//    @Test
//    public void some_architecture_rule() {
////        JavaClasses importedClasses = new ClassFileImporter().withImportOption(new ImportOption.DoNotIncludeTests())
////                .importPackages("com.meal.mechant");
//
//        ArchRule rule = classes().that().areEnums().should().resideInAnyPackage("..constant..")
//                .andShould().haveSimpleNameEndingWith("Enum");
//
//        rule.check(importedClasses);
//    }

    @ArchTest
    public static final ArchRule myRule = classes()
            .that().resideInAPackage("..service..")
            .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");
    @ArchTest
    public static final ArchRule myRule2 = classes().should(adhereToPlantUmlDiagram(Paths.get("src/test/resources/packageRule.puml"), consideringOnlyDependenciesInDiagram()));
}*/
