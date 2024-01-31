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

    private static ProjectRepositoryTestData TEST_DATA;

    @BeforeEach
    public void setUp() {
        TEST_DATA = ProjectRepositoryTestDataBuilder.projectRepositoryTestData(testEntityManager);
    }

    @Test
    void test_findAllByOwnerOrderByNameShouldReturnAllProjectsOnCorrectUser()  {
        //given
        User owner = TEST_DATA.project1And2Owner();
        Pageable pageable = PageRequest.of(0, 2);

        //when
        Page<Project> projects = projectRepository.findAllByOwnerOrderByName(owner, pageable);

        //then
        assertEquals(2L, projects.getTotalElements());
        assertTrue(projects.getContent().containsAll(List.of(TEST_DATA.project1(), TEST_DATA.project2())));
    }

    @Test
    void test_findAllByOwnerOrderByNameShouldReturn1ProjectOnPageOfSize1() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        Pageable pageable = PageRequest.of(0, 1);

        //when
        Page<Project> projects = projectRepository.findAllByOwnerOrderByName(owner, pageable);

        //then
        assertEquals(2L, projects.getTotalElements());
        assertEquals(1L, projects.getNumberOfElements());
        assertEquals(TEST_DATA.project1(), projects.getContent().get(0));
    }

    @Test
    void test_findAllByOwnerOrderByNameShouldReturn1ProjectOnPageOfSize1OnPage2() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        Pageable pageable = PageRequest.of(1, 1);

        //when
        Page<Project> projects = projectRepository.findAllByOwnerOrderByName(owner, pageable);

        //then
        assertEquals(2L, projects.getTotalElements());
        assertEquals(1L, projects.getNumberOfElements());
        assertEquals(TEST_DATA.project2(), projects.getContent().get(0));
    }

    @Test
    void test_findAllByOwnerOrderByNaeShouldReturnNoProjectsWhenUserOwns0Projects() {
        //given
        User user = TEST_DATA.nonProjectMember();
        Pageable pageable = PageRequest.of(0, 1);

        //when
        Page<Project> projects = projectRepository.findAllByOwnerOrderByName(user, pageable);

        //then
        assertEquals(0L, projects.getTotalElements());
    }

    @Test
    void test_findByIdWithProjectMemberShouldReturnProjectWhenUserIsOwner() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        long id = TEST_DATA.project1().getProjectId();

        //when
        Optional<Project> project = projectRepository.findByIdWithProjectMember(id, owner);

        //then
        assertTrue(project.isPresent());
        assertEquals(TEST_DATA.project1(), project.get());
    }

    @Test
    void test_findByIdWithProjectMemberShouldReturnProjectWhenUserIsMember() {
        //given
        User member = TEST_DATA.project1Member();
        long id = TEST_DATA.project1().getProjectId();

        //when
        Optional<Project> project = projectRepository.findByIdWithProjectMember(id, member);

        //then
        assertTrue(project.isPresent());
        assertEquals(TEST_DATA.project1(), project.get());
    }

    @Test
    void test_findByIdWithProjectMemberShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        long id = 100L;

        //when
        Optional<Project> project = projectRepository.findByIdWithProjectMember(id, owner);

        //then
        assertTrue(project.isEmpty());
    }

    @Test
    void test_findByIdWithProjectMemberShouldReturnEmptyOptionalOnNonMemberUser() {
        //given
        User nonMember = TEST_DATA.nonProjectMember();
        long id = TEST_DATA.project1().getProjectId();

        //when
        Optional<Project> project = projectRepository.findByIdWithProjectMember(id, nonMember);

        //then
        assertTrue(project.isEmpty());
    }

    @Test
    void test_findByProjectIdAndOwnerShouldReturnProjectWhenUserIsOwner() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        long id = TEST_DATA.project1().getProjectId();

        //when
        Optional<Project> project = projectRepository.findByProjectIdAndOwner(id, owner);

        //then
        assertTrue(project.isPresent());
        assertEquals(TEST_DATA.project1(), project.get());
    }

    @Test
    void test_findByProjectIdAndOwnerShouldReturnEmptyOptionalOnIncorrectId() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        long id = 100L;

        //when
        Optional<Project> project = projectRepository.findByProjectIdAndOwner(id, owner);

        //then
        assertTrue(project.isEmpty());
    }

    @Test
    void test_findByProjectIdAndOwnerShouldReturnEmptyOptionalOnNonMemberUser() {
        //given
        User nonMember = TEST_DATA.nonProjectMember();
        long id = TEST_DATA.project1().getProjectId();

        //when
        Optional<Project> project = projectRepository.findByProjectIdAndOwner(id, nonMember);

        //then
        assertTrue(project.isEmpty());
    }

    @Test
    void test_findByProjectIdAndOwnerShouldReturnEmptyOptionalOnMemberUser() {
        //given
        User member = TEST_DATA.project1Member();
        long id = TEST_DATA.project1().getProjectId();

        //when
        Optional<Project> project = projectRepository.findByProjectIdAndOwner(id, member);

        //then
        assertTrue(project.isEmpty());
    }

    @Test
    void test_existsByProjectMemberAndProjectId_ShouldReturnTrueIfUserIsOwner() {
        //given
        User owner = TEST_DATA.project1And2Owner();
        long id = TEST_DATA.project1().getProjectId();

        //when
        boolean exists = projectRepository.existsByProjectMemberAndProjectId(owner, id);

        //then
        assertTrue(exists, "User should exist");
    }

    @Test
    void test_existsByProjectMemberAndProjectId_ShouldReturnTrueIfUserIsMember() {
        //given
        User member = TEST_DATA.project1Member();
        long id = TEST_DATA.project1().getProjectId();

        //when
        boolean exists = projectRepository.existsByProjectMemberAndProjectId(member, id);

        //then
        assertTrue(exists, "User should exist");
    }

    @Test
    void test_existsByProjectMemberAndProjectId_ShouldReturnFalseIsUserIsNotAMemberNorOwner() {
        //given
        User nonMember = TEST_DATA.nonProjectMember();
        long id = TEST_DATA.project1().getProjectId();

        //when
        boolean exists = projectRepository.existsByProjectMemberAndProjectId(nonMember, id);

        //then
        assertFalse(exists, "User should not exist");
    }


}
