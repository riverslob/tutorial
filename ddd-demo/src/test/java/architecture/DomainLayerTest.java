package architecture;

import dev.demo.ddd.blogservice.common.archrules.AggregateRoot;
import dev.demo.ddd.blogservice.common.archrules.Criteria;
import dev.demo.ddd.blogservice.common.archrules.DomainEntity;
import dev.demo.ddd.blogservice.common.archrules.Factory;
import dev.demo.ddd.blogservice.common.archrules.Persistence;
import dev.demo.ddd.blogservice.common.archrules.ReadModel;
import dev.demo.ddd.blogservice.common.archrules.DomainService;
import dev.demo.ddd.blogservice.common.archrules.ValueObject;
import dev.demo.ddd.blogservice.domain.common.DomainException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class DomainLayerTest extends ArchitectureTest {

    @Nested
    class all {

        @Test
        void domain_layer_depend_on_rule() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .should().onlyDependOnClassesThat().resideInAnyPackage("java..", "..domain..","..common.archrules..")
                    .as("The domain layer should only depend on the classes in the package of " +
                            "java and domain.")
                    .check(classes);
        }

        @Test
        void domain_layer_annotated_and_extend_rule() {
            classes()
                    .that().resideInAPackage("..domain.models..")

                    .should().beAnnotatedWith(DomainEntity.class)
                    .orShould().beAnnotatedWith(AggregateRoot.class)
                    .orShould().beAnnotatedWith(ValueObject.class)
                    .orShould().beAnnotatedWith(ReadModel.class)
                    .orShould().beAnnotatedWith(DomainService.class)
                    .orShould().beAnnotatedWith(Factory.class)
                    .orShould().beAnnotatedWith(Persistence.class)
                    .orShould().beAssignableTo(DomainException.class)
                    .orShould().beAnnotatedWith(Criteria.class)
                    .as("The models in the domain should annotated one of the interfaces / classes in " +
                            "Entity, AggregateRoot, ValueObject, ReadModel, WriteModel, " +
                            "Service, Policy, Factory, Repository, DomainException, Criteria.")
                    .check(classes);
        }
    }

    @Nested
    class service {

        @Test
        void services_should_be_named_ending_with_Service() {
            classes().that().resideInAPackage("..domain..")
                    .and().areAnnotatedWith(DomainService.class)
                    .should().haveSimpleNameEndingWith("Service")
                    .as("The domain services should be named ending with 'Service'.")
                    .check(classes);
        }

        @Test
        void services_should_annotate_Service() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .and().haveSimpleNameEndingWith("Service")
                    .and().areNotInterfaces()
                    .should().beAnnotatedWith(DomainService.class)
                    .as("The domain services should annotated 'Service' interface.")
                    .check(classes);
        }
    }

    @Nested
    class factory {

        @Test
        void factories_should_be_named_ending_with_Factory() {
            classes().that().resideInAPackage("..domain..")
                    .and().areAnnotatedWith(Factory.class)
                    .should().haveSimpleNameEndingWith("Factory")
                    .as("The domain factories should be named ending with 'Factory'.")
                    .check(classes);
        }

        @Test
        void factories_should_annotate_Factory() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .and().haveSimpleNameEndingWith("Factory")
                    .and().areNotInterfaces()
                    .should().beAnnotatedWith(Factory.class)
                    .as("The domain factories should annotated 'Factory' interface.")
                    .check(classes);
        }
    }

    @Nested
    class repository {

        @Test
        void repositories_should_be_named_ending_with_Repository() {
            classes().that().resideInAPackage("..domain..")
                    .and().areAnnotatedWith(Persistence.class)
                    .should().haveSimpleNameEndingWith("Repository")
                    .as("The repositories should be named ending with 'Repository'.")
                    .check(classes);
        }

        @Test
        void repositories_should_annotate_Repository() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .and().haveSimpleNameEndingWith("Repository")
                    .and().doNotHaveSimpleName("Repository")
                    .should().beAnnotatedWith(Persistence.class)
                    .as("The repositories should be annotated with 'Persistence' interface.")
                    .check(classes);
        }
    }

    @Nested
    class domain_exception {

        @Test
        void domain_exceptions_should_be_named_ending_with_Exception() {
            classes().that().resideInAPackage("..domain..")
                    .and().areAssignableTo(DomainException.class)
                    .should().haveSimpleNameEndingWith("Exception")
                    .as("The domain exceptions should be named ending with 'Exception'.")
                    .check(classes);
        }

        @Test
        void domain_exceptions_should_extend_DomainException() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .and().haveSimpleNameEndingWith("Exception")
                    .should().beAssignableTo(DomainException.class)
                    .as("The domain exceptions should extend DomainException.")
                    .check(classes);
        }
    }

    @Nested
    class criteria {

        @Test
        void criteria_should_be_named_ending_with_QueryCondition() {
            classes().that().resideInAPackage("..domain..")
                    .and().areAnnotatedWith(Criteria.class)
                    .should().haveSimpleNameEndingWith("QueryCondition")
                    .as("The criteria should be named ending with 'Criteria'.")
                    .check(classes);
        }

        @Test
        void criteria_should_annotate_Criteria() {
            classes()
                    .that().resideInAPackage("..domain..")
                    .and().haveSimpleNameEndingWith("Criteria")
                    .should().beAnnotatedWith(Criteria.class)
                    .as("The criteria should be annotated with Criteria.")
                    .check(classes);
        }
    }
}
