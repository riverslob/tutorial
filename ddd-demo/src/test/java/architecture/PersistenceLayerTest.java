package architecture;

import dev.demo.ddd.blogservice.common.archrules.Persistence;
import dev.demo.ddd.blogservice.common.archrules.PersistenceObject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class PersistenceLayerTest extends ArchitectureTest {

    @Nested
    class repository_impl {

        @Test
        void repository_impls_be_public() {
            classes()
                    .that().resideInAPackage("..outbound.persistence..")
                    .and().areMetaAnnotatedWith(Persistence.class)
                    .should().bePublic()
                    .as("The repository impls should be package private.")
                    .check(classes);
        }

        @Test
        void repository_impls_should_be_named_ending_with_RepositoryImpl() {
            classes()
                    .that().resideInAPackage("..outbound.persistence..")
                    .and().areMetaAnnotatedWith(Persistence.class)
                    .should().haveSimpleNameEndingWith("RepositoryImpl")
                    .as("The repository impls should be named ending with 'RepositoryImpl'.")
                    .check(classes);
        }

       /* @Test
        void repository_impls_should_annotate_Repository() {
            classes()
                    .that().resideInAPackage("..outbound.persistence..")
                    .and().haveSimpleNameEndingWith("RepositoryImpl")
                    .should().implement(Persistence.class)
                    .as("The repository impls should implement has annotated with 'Persistence' interface.")
                    .check(classes);
        }*/
    }

    @Nested
    class persistence_dto {

        @Test
        void persistence_objects_be_public() {
            classes()
                    .that().resideInAPackage("..outbound.persistence..")
                    .and().areAnnotatedWith(PersistenceObject.class)
                    .should().bePublic()
                    .as("The persistence objects should be public.")
                    .check(classes);
        }

        @Test
        void persistence_objects_should_be_named_ending_with_PO() {
            classes()
                    .that().resideInAPackage("..outbound.persistence..")
                    .and().areAnnotatedWith(PersistenceObject.class)
                    .should().haveSimpleNameEndingWith("PO")
                    .as("The persistence objects should be named ending with 'PO'.")
                    .check(classes);
        }

        @Test
        void persistence_objects_should_annotate_PersistenceObject() {
            classes()
                    .that().resideInAPackage("..outbound.persistence..")
                    .and().haveSimpleNameEndingWith("PO")
                    .and().areNotInterfaces()
                    .should().beAnnotatedWith(PersistenceObject.class)
                    .as("The persistence objects should annotated 'PersistenceObject' interface.")
                    .check(classes);
        }
    }
}
