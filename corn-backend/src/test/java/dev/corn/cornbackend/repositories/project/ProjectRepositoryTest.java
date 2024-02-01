package dev.corn.cornbackend.repositories.project;

import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.repositories.project.data.ProjectRepositoryTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProjectRepository projectRepository;

    private static ProjectRepositoryTestData TEST_DATA = null;

    private static final String OPTIONAL_PRESENT = "Project optional should be present";
    private static final String OPTIONAL_EMPTY = "Project optional should be empty";
    private static final String PAGE_CORRECT_TOTAL_ELEMENTS = "Project page should have correct total elements number";
    private static final String PAGE_CORRECT_ITEMS = "Project page should contain correct elements";
    private static final String PAGE_CORRECT_NUMBER_OF_ELEMENTS = "Project page should have correct number of elements";
    private static final String PROJECT_EQUAL = "Projects should be equal";
    private static final String USER_EXIST = "User should exist";

    @BeforeEach
    public final void setUp() {
        TEST_DATA = ProjectRepositoryTestDataBuilder.projectRepositoryTestData(testEntityManager);
    }

    @Test
    final void test_findAllByOwnerOrderByNameShouldReturnAllProjectsOnCorrectUser()  {
        //given
        User owner = TEST_DATA.project1And2Owner();
        Pageable pageable = PageRequest.of(0, 2);

        //when
        Page<Project> projects = projectRepository.findAllByOwnerOrderByName(owner, pageable);

        //then
        assertEquals(2L, projects.getTotalElements(), PAGE_CORRECT_TOTAL_ELEMENTS);
        assertTrue(projects.getContent().containsAll(List.of(TEST_DATA.project1(), TEST_DATA.project2())),
                PAGE_CORRECT_ITEMS);
    }

    @Test
    final void test_findAllByOwnerOrderByNameShouldReturn1ProjectOnPageOfSize1() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        Pageable pageable = PageRequest.of(0, 1);

        //when
        Page<Project> projects = projectRepository.findAllByOwnerOrderByName(owner, pageable);

        //then
        assertEquals(2L, projects.getTotalElements(), PAGE_CORRECT_TOTAL_ELEMENTS);
        assertEquals(1, projects.getNumberOfElements(), PAGE_CORRECT_NUMBER_OF_ELEMENTS);
        assertEquals(TEST_DATA.project1(), projects.getContent().get(0), PAGE_CORRECT_ITEMS);
    }

    @Test
    final void test_findAllByOwnerOrderByNameShouldReturn1ProjectOnPageOfSize1OnPage2() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        Pageable pageable = PageRequest.of(1, 1);

        //when
        Page<Project> projects = projectRepository.findAllByOwnerOrderByName(owner, pageable);

        //then
        assertEquals(2L, projects.getTotalElements(), PAGE_CORRECT_TOTAL_ELEMENTS);
        assertEquals(1, projects.getNumberOfElements(), PAGE_CORRECT_NUMBER_OF_ELEMENTS);
        assertEquals(TEST_DATA.project2(), projects.getContent().get(0), PAGE_CORRECT_ITEMS);
    }

    @Test
    final void test_findAllByOwnerOrderByNameShouldReturnNoProjectsWhenUserOwns0Projects() {
        //given
        User user = TEST_DATA.nonProjectMember();
        Pageable pageable = PageRequest.of(0, 1);

        //when
        Page<Project> projects = projectRepository.findAllByOwnerOrderByName(user, pageable);

        //then
        assertEquals(0L, projects.getTotalElements(), PAGE_CORRECT_TOTAL_ELEMENTS);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnProjectWhenUserIsOwner() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        long id = TEST_DATA.project1().getProjectId();

        //when
        Optional<Project> project = projectRepository.findByIdWithProjectMember(id, owner);

        //then
        assertTrue(project.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.project1(), project.get(), PROJECT_EQUAL);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnProjectWhenUserIsMember() {
        //given
        User member = TEST_DATA.project1Member();
        long id = TEST_DATA.project1().getProjectId();

        //when
        Optional<Project> project = projectRepository.findByIdWithProjectMember(id, member);

        //then
        assertTrue(project.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.project1(), project.get(), PROJECT_EQUAL);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        long id = 100L;

        //when
        Optional<Project> project = projectRepository.findByIdWithProjectMember(id, owner);

        //then
        assertTrue(project.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByIdWithProjectMemberShouldReturnEmptyOptionalOnNonMemberUser() {
        //given
        User nonMember = TEST_DATA.nonProjectMember();
        long id = TEST_DATA.project1().getProjectId();

        //when
        Optional<Project> project = projectRepository.findByIdWithProjectMember(id, nonMember);

        //then
        assertTrue(project.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByProjectIdAndOwnerShouldReturnProjectWhenUserIsOwner() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        long id = TEST_DATA.project1().getProjectId();

        //when
        Optional<Project> project = projectRepository.findByProjectIdAndOwner(id, owner);

        //then
        assertTrue(project.isPresent(), OPTIONAL_PRESENT);
        assertEquals(TEST_DATA.project1(), project.get(), PROJECT_EQUAL);
    }

    @Test
    final void test_findByProjectIdAndOwnerShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        long id = 100L;

        //when
        Optional<Project> project = projectRepository.findByProjectIdAndOwner(id, owner);

        //then
        assertTrue(project.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByProjectIdAndOwnerShouldReturnEmptyOptionalOnNonMemberUser() {
        //given
        User nonMember = TEST_DATA.nonProjectMember();
        long id = TEST_DATA.project1().getProjectId();

        //when
        Optional<Project> project = projectRepository.findByProjectIdAndOwner(id, nonMember);

        //then
        assertTrue(project.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_findByProjectIdAndOwnerShouldReturnEmptyOptionalOnMemberUser() {
        //given
        User member = TEST_DATA.project1Member();
        long id = TEST_DATA.project1().getProjectId();

        //when
        Optional<Project> project = projectRepository.findByProjectIdAndOwner(id, member);

        //then
        assertTrue(project.isEmpty(), OPTIONAL_EMPTY);
    }

    @Test
    final void test_existsByProjectMemberAndProjectId_ShouldReturnTrueIfUserIsOwner() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        long id = TEST_DATA.project1().getProjectId();

        //when
        boolean exists = projectRepository.existsByProjectMemberAndProjectId(owner, id);

        //then
        assertTrue(exists, USER_EXIST);
    }

    @Test
    final void test_existsByProjectMemberAndProjectId_ShouldReturnTrueIfUserIsMember() {
        //given
        User member = TEST_DATA.project1Member();
        long id = TEST_DATA.project1().getProjectId();

        //when
        boolean exists = projectRepository.existsByProjectMemberAndProjectId(member, id);

        //then
        assertTrue(exists, USER_EXIST);
    }

    @Test
    final void test_existsByProjectMemberAndProjectId_ShouldReturnFalseIsUserIsNotAMemberNorOwner() {
        //given
        User nonMember = TEST_DATA.nonProjectMember();
        long id = TEST_DATA.project1().getProjectId();

        //when
        boolean exists = projectRepository.existsByProjectMemberAndProjectId(nonMember, id);

        //then
        assertFalse(exists, "User should not exist");
    }


}
