package dev.corn.cornbackend.config;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentRequest;
import dev.corn.cornbackend.api.backlog.comment.interfaces.BacklogItemCommentService;
import dev.corn.cornbackend.api.backlog.item.data.BacklogItemRequest;
import dev.corn.cornbackend.api.project.interfaces.ProjectService;
import dev.corn.cornbackend.api.project.member.interfaces.ProjectMemberService;
import dev.corn.cornbackend.api.sprint.data.SprintRequest;
import dev.corn.cornbackend.api.sprint.data.SprintResponse;
import dev.corn.cornbackend.api.sprint.interfaces.SprintService;
import dev.corn.cornbackend.api.user.interfaces.UserService;
import dev.corn.cornbackend.entities.backlog.item.BacklogItem;
import dev.corn.cornbackend.entities.backlog.item.enums.ItemStatus;
import dev.corn.cornbackend.entities.backlog.item.enums.ItemType;
import dev.corn.cornbackend.entities.backlog.item.interfaces.BacklogItemRepository;
import dev.corn.cornbackend.entities.project.Project;
import dev.corn.cornbackend.entities.project.interfaces.ProjectRepository;
import dev.corn.cornbackend.entities.project.member.ProjectMember;
import dev.corn.cornbackend.entities.project.member.interfaces.ProjectMemberRepository;
import dev.corn.cornbackend.entities.sprint.Sprint;
import dev.corn.cornbackend.entities.sprint.interfaces.SprintRepository;
import dev.corn.cornbackend.entities.user.User;
import dev.corn.cornbackend.entities.user.data.UserResponse;
import dev.corn.cornbackend.entities.user.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class PlaceholderData implements CommandLineRunner {

    private final UserService userService;
    private final UserRepository userRepository;
    private final SprintService sprintService;
    private final SprintRepository sprintRepository;
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final BacklogItemRepository backlogItemRepository;
    private final BacklogItemCommentService backlogItemCommentService;
    private final ProjectMemberService projectMemberService;
    private final ProjectMemberRepository projectMemberRepository;
    private final Random random = new Random(0);

    @Value("${CREATE_PLACEHOLDER_DATA:false}")
    private String CREATE_PLACEHOLDER_DATA;

    @Override
    public void run(String... args) {
        if (!"true".equalsIgnoreCase(CREATE_PLACEHOLDER_DATA)) {
            return;
        }
        List<ItemType> typesPool = List.of(
                ItemType.TASK, ItemType.TASK, ItemType.TASK, ItemType.BUG,
                ItemType.TASK, ItemType.TASK, ItemType.TASK, ItemType.BUG,
                ItemType.BUG, ItemType.EPIC, ItemType.STORY
        );
        List<ItemStatus> statusesPool = List.of(
                ItemStatus.TODO, ItemStatus.TODO, ItemStatus.TODO, ItemStatus.TODO,
                ItemStatus.IN_PROGRESS, ItemStatus.IN_PROGRESS,
                ItemStatus.DONE, ItemStatus.DONE, ItemStatus.DONE
        );
        List<User> users = userRepository.findAllById(Stream.of(
                userService.registerUser("Jan", "Kowalski", "jan"),
                userService.registerUser("Andrzej", "Switch", "andrzej"),
                userService.registerUser("John", "Doe", "john"),
                userService.registerUser("Jane", "Doe", "jane"),
                userService.registerUser("Alice", "Smith", "alice"),
                userService.registerUser("Bob", "Johnson", "bob")
        ).map(UserResponse::userId).toList());

        User projectOwner = drawRandom(users);
        projectService.addNewProject("Sample Project", projectOwner);
        Project project = projectRepository.findAllByOwnerOrderByName(projectOwner, Pageable.ofSize(1))
                .stream().findFirst().get();

        users.stream()
                .filter(user -> user.getUserId() != projectOwner.getUserId())
                .forEach(user ->
                        projectMemberService.addMemberToProject(user.getUsername(), project.getProjectId(), projectOwner)
                );

        List<ProjectMember> members = projectMemberRepository.findAll();

        List<SprintResponse> sprints = List.of(
                sprintService.addNewSprint(new SprintRequest(
                        project.getProjectId(), "Sprint 1", LocalDate.now(), LocalDate.now().plusDays(7),
                        "pierwszy sprint projektu"
                ), projectOwner),
                sprintService.addNewSprint(new SprintRequest(
                        project.getProjectId(), "Sprint 2", LocalDate.now().plusDays(7), LocalDate.now().plusDays(14),
                        "drugi sprint projektu"
                ), projectOwner)
        );

        List<Sprint> allSprints = sprintRepository.findAll();

        List<BacklogItem> backlogItems = Arrays.stream(SAMPLE_BACKLOG_ITEMS)
                .map(item -> new BacklogItem(0,
                        item[0], item[1],
                        drawRandom(statusesPool),
                        LocalDate.now().plusDays(random.nextInt(14)),
                        Collections.emptyList(),
                        drawRandom(members),
                        drawRandom(allSprints),
                        project,
                        drawRandom(typesPool)
                )).map(backlogItemRepository::save).toList();

        for (int i = 0; i < SAMPLE_BACKLOG_ITEMS.length / 4; i++) {
            for (int j = 0; j < random.nextInt(4); j++) {
                long backlogItemId = drawRandom(backlogItems).getBacklogItemId();
                User commenter = drawRandom(users);
                backlogItemCommentService.addNewComment(new BacklogItemCommentRequest(
                        drawRandom(SAMPLE_COMMENTS), backlogItemId
                ), commenter);
            }
        }
    }

    private <T> T drawRandom(List<T> list) {
        ArrayList<T> tmp = new ArrayList<>(list);
        Collections.shuffle(tmp, random);
        return tmp.get(0);
    }

    private final String[][] SAMPLE_BACKLOG_ITEMS = {
            {"Develop Feature X", "Implement and test the new feature X to enhance user experience."},
            {"Fix Bug in Login Module", "Investigate and resolve the login module bug reported by users."},
            {"Refactor Database Access", "Optimize database access code for better performance and maintainability."},
            {"Create User Dashboard", "Design and implement a user dashboard for monitoring key metrics."},
            {"Update UI Styling", "Apply updated styling to improve the overall look and feel of the application."},
            {"Implement Notification System", "Create a system for sending notifications to users based on their preferences."},
            {"Add Multi-language Support", "Integrate multi-language support for the application."},
            {"Optimize Image Loading", "Improve the efficiency of image loading for a smoother user experience."},
            {"Integrate Payment Gateway", "Connect the application with a payment gateway for processing transactions."},
            {"Enhance Search Functionality", "Improve the search feature to provide more accurate and relevant results."},
            {"Upgrade Server Infrastructure", "Upgrade the server infrastructure to handle increased traffic and improve scalability."},
            {"Implement OAuth Authentication", "Integrate OAuth for secure and convenient user authentication."},
            {"Design and Implement Chat Feature", "Create a real-time chat feature for users to communicate within the application."},
            {"Improve Error Handling", "Enhance error handling to provide clearer messages and improve user experience."},
            {"Upgrade Third-party Libraries", "Update third-party libraries to the latest versions for security and performance benefits."},
            {"Create Mobile Responsive Design", "Optimize the application's design for a seamless experience on mobile devices."},
            {"Implement Two-Factor Authentication", "Enhance security by implementing two-factor authentication for user accounts."},
            {"Add Social Media Sharing", "Integrate social media sharing functionality to allow users to share content."},
            {"Optimize Backend API Calls", "Optimize backend API calls to reduce response times and improve overall performance."},
            {"Implement User Permissions", "Create a system for managing user permissions and roles within the application."},
            {"Improve Accessibility", "Enhance accessibility features to ensure the application is usable by people with disabilities."},
            {"Create Onboarding Tutorial", "Design and implement a tutorial to guide new users through the application."},
            {"Implement Automated Testing", "Introduce automated testing to ensure code reliability and catch bugs early."},
            {"Upgrade Data Encryption", "Enhance data encryption protocols to strengthen overall data security."},
            {"Implement Dark Mode", "Add a dark mode option for users who prefer a darker color scheme."},
            {"Integrate Analytics Tracking", "Implement analytics tracking to gather insights into user behavior and application usage."},
            {"Enhance File Upload Functionality", "Improve file upload features for a more seamless user experience."},
            {"Update Terms of Service", "Review and update the application's terms of service for legal compliance."},
            {"Implement User Profile Customization", "Allow users to customize their profiles with additional information and preferences."},
            {"Create In-App Notifications", "Develop a system for in-app notifications to keep users informed of important updates."},
            {"Improve Data Validation", "Enhance data validation to ensure the accuracy and integrity of user-inputted information."},
            {"Implement Geolocation Features", "Integrate geolocation features for location-based services within the application."},
            {"Upgrade Frontend Framework", "Update the frontend framework to leverage the latest features and improvements."},
            {"Optimize Database Indexing", "Improve database indexing to enhance query performance."},
            {"Implement Auto-Save Drafts", "Create an auto-save feature for drafts to prevent data loss."},
            {"Integrate Video Streaming", "Add video streaming capabilities for multimedia content."},
            {"Improve Email Notification System", "Enhance the email notification system for timely and reliable communications."},
            {"Implement User Surveys", "Introduce user surveys to gather feedback and improve user satisfaction."},
            {"Upgrade Password Security", "Enhance password security measures to protect user accounts."},
            {"Create Admin Dashboard", "Develop a dashboard for administrators to manage and monitor the application."},
            {"Implement Cross-Browser Compatibility", "Ensure the application functions smoothly across different web browsers."},
            {"Enhance Data Backup Mechanism", "Improve the data backup mechanism to prevent data loss in case of failures."},
            {"Create Interactive Data Visualizations", "Implement interactive data visualizations for better data understanding."},
            {"Optimize Mobile App Performance", "Optimize performance for the mobile app version of the application."},
            {"Implement Gamification Elements", "Introduce gamification elements to enhance user engagement."},
            {"Upgrade SSL/TLS Certificates", "Renew and update SSL/TLS certificates for secure data transmission."},
            {"Improve Documentation", "Enhance documentation for developers and end-users for better understanding."},
    };

    private final List<String> SAMPLE_COMMENTS = List.of(
            "Good progress on Feature X. Keep it up!",
            "Facing issues with the login bug. Need more details to proceed.",
            "The database refactor looks promising. Any specific challenges?",
            "User dashboard design approved. Start implementation phase.",
            "The updated UI styling is a great improvement. Nice work!",
            "Feature X implementation completed. Ready for testing.",
            "Resolved the login module bug. Testing required for verification.",
            "Database access code optimized successfully.",
            "User dashboard backend logic implemented. Frontend in progress.",
            "UI styling adjustments made based on user feedback.",
            "Feature X testing phase started. Report any issues.",
            "Login module bug fix verified. Ready for deployment.",
            "Database access refactor reviewed and approved.",
            "User dashboard frontend progress update: 50% complete.",
            "Updated UI styling adjustments based on team feedback.",
            "Feature X passed initial testing. Few minor tweaks needed.",
            "Login module bug fix deployed to production successfully.",
            "Database access refactor performance testing underway.",
            "User dashboard frontend completed. Backend integration in progress.",
            "Final UI styling changes approved. Ready for production.",
            "Feature X officially released. Communicate to users.",
            "Login module bug fix confirmed resolved. No reported issues.",
            "Database access refactor performance results positive.",
            "User dashboard integration complete. Testing phase initiated.",
            "Positive user feedback on the new UI styling. Well done!",
            "Feature X documentation updated. Notify documentation team.",
            "Login module bug fix documentation reviewed. Ready for publishing.",
            "Database access refactor documentation in progress.",
            "User dashboard documentation initiated. Collaboration with tech writers.",
            "UI styling documentation updated. Check for accuracy.",
            "Feature X success metrics meeting scheduled. Prepare data.",
            "Login module bug fix success metrics reviewed. Positive impact noted.",
            "Database access refactor success metrics written."
    );

}
