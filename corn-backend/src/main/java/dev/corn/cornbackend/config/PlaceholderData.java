package dev.corn.cornbackend.config;

import dev.corn.cornbackend.api.backlog.comment.data.BacklogItemCommentRequest;
import dev.corn.cornbackend.api.backlog.comment.interfaces.BacklogItemCommentService;
import dev.corn.cornbackend.api.project.interfaces.ProjectService;
import dev.corn.cornbackend.api.project.member.interfaces.ProjectMemberService;
import dev.corn.cornbackend.api.sprint.data.SprintRequest;
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
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
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
    private final EntityManager entityManager;
    private final Random random = new Random(0);

    @Value("${CREATE_PLACEHOLDER_DATA:false}")
    private String CREATE_PLACEHOLDER_DATA;

    @Override
    @Transactional
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
        Arrays.stream(PROJECT_NAMES)
                .forEach(name -> {
                    projectService.addNewProject(name, projectOwner);
                });
        Project[] projects = projectRepository.findAllByOwnerOrderByName(projectOwner, Pageable.ofSize(PROJECT_NAMES.length))
                .stream().toList().toArray(new Project[0]);
        Project project = projects[0];

        users.stream()
                .filter(user -> user.getUserId() != projectOwner.getUserId())
                .forEach(user -> Arrays.stream(projects).forEach(currentProject ->
                        projectMemberService.addMemberToProject(user.getUsername(), currentProject.getProjectId(), projectOwner)));

        List<ProjectMember> members = projectMemberRepository.findAllByProject(project, Pageable.ofSize(users.size()))
                .stream().toList();

        LocalDate prevDate = LocalDate.now();

        for (int i = 0; i < 8; i++) {
            sprintService.addNewSprint(new SprintRequest(
                    project.getProjectId(), String.format("Sprint %d", i), prevDate, prevDate.plusDays(7),
                    String.format("Sprintd %d description", i)
            ), projectOwner);

            prevDate = prevDate.plusDays(8L);
        }

        List<Sprint> allSprints = sprintRepository.findAll();

        List<BacklogItem> backlogItems = new ArrayList<>();

        for (Sprint sprint : allSprints) {
            backlogItems.addAll(IntStream.range(0, 8 + random.nextInt(8))
                    .mapToObj(i -> drawRandom(SAMPLE_BACKLOG_ITEMS))
                    .map(item -> new BacklogItem(0,
                            item[0], item[1],
                            drawRandom(statusesPool),
                            null,
                            Collections.emptyList(),
                            drawRandom(members),
                            sprint,
                            project,
                            drawRandom(typesPool)
                    )).map(backlogItemRepository::save).toList());
        }

        for (int i = 0; i < SAMPLE_BACKLOG_ITEMS.length / 4; i++) {
            for (int j = 0; j < random.nextInt(4); j++) {
                long backlogItemId = drawRandom(backlogItems).getBacklogItemId();
                User commenter = drawRandom(users);
                for (int k = 0; k < 5; k++) {
                    backlogItemCommentService.addNewComment(new BacklogItemCommentRequest(
                            drawRandom(SAMPLE_COMMENTS), backlogItemId
                    ), commenter);
                }
                backlogItemCommentService.addNewComment(new BacklogItemCommentRequest(
                        LONG_STRING, backlogItemId), users.get(4));
            }
        }

        Arrays.stream(SAMPLE_BACKLOG_ITEMS_WITHOUT_SPRINT)
                .map(item -> new BacklogItem(0,
                        item[0], item[1],
                        ItemStatus.TODO,
                        null,
                        Collections.emptyList(),
                        null,
                        null,
                        project,
                        drawRandom(typesPool)))
                .forEach(backlogItemRepository::save);

        int dayShift = 24;

        backlogItemRepository.saveAll(backlogItems.stream()
                .filter(item -> item.getSprint().isStartAfter(LocalDate.now().plusDays(dayShift)))
                .peek(item -> item.setStatus(ItemStatus.TODO))
                .toList()
        );
        backlogItemRepository.saveAll(backlogItems.stream()
                .filter(item -> item.getSprint().isEndBefore(LocalDate.now().plusDays(dayShift)))
                .peek(item -> {
                    if (random.nextDouble() < 0.53) {
                        item.setStatus(ItemStatus.DONE);
                        LocalDate start = item.getSprint().getStartDate();
                        LocalDate end = item.getSprint().getEndDate();
                        int daysBetween = (int) ChronoUnit.DAYS.between(start, end);
                        item.setTaskFinishDate(start.plusDays(1+random.nextInt(daysBetween-2)).minusDays(dayShift));
                    } else {
                        item.setStatus(random.nextDouble() < 0.5 ? ItemStatus.IN_PROGRESS : ItemStatus.TODO);
                    }
                }).toList()
        );

        Query query = entityManager.createNativeQuery("UPDATE sprint SET " +
                "end_date = end_date - INTERVAL '"+dayShift+" days', " +
                "start_date = start_date - INTERVAL '"+dayShift+" days';");
        query.executeUpdate();
    }

    private <T> T drawRandom(List<T> list) {
        ArrayList<T> tmp = new ArrayList<>(list);
        Collections.shuffle(tmp, random);
        return tmp.get(0);
    }

    private <T> T drawRandom(T[] array) {
        return array[random.nextInt(array.length)];
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

    private final String[][] SAMPLE_BACKLOG_ITEMS_WITHOUT_SPRINT = {
            {"Implement Feature Z", "Develop and integrate Feature Z to improve application functionality."},
            {"Resolve Performance Bottlenecks", "Identify and address performance bottlenecks to enhance application speed."},
            {"Refactor Legacy Codebase", "Restructure legacy codebase to improve maintainability and readability."},
            {"Design New Landing Page", "Create a captivating landing page to attract more visitors to the application."},
            {"Implement Offline Mode", "Develop offline mode functionality to allow users to access key features without internet connectivity."},
            {"Enhance Data Visualization Tools", "Upgrade data visualization tools to provide more insights to users."},
            {"Upgrade Hosting Infrastructure", "Migrate to a more robust hosting infrastructure for better reliability."},
            {"Create Interactive User Guides", "Develop interactive user guides to assist users in navigating the application."},
            {"Implement Real-Time Collaboration", "Introduce real-time collaboration features for enhanced teamwork."},
            {"Optimize Frontend Performance", "Optimize frontend performance to reduce load times and improve user experience."},
            {"Integrate Voice Search", "Incorporate voice search functionality for hands-free navigation."},
            {"Enhance Data Security Measures", "Implement additional security measures to protect user data from threats."},
            {"Upgrade User Feedback Mechanism", "Enhance the system for collecting and analyzing user feedback."},
            {"Implement AI-driven Recommendations", "Integrate AI algorithms to provide personalized recommendations to users."},
            {"Optimize Database Schema", "Optimize database schema for improved data organization and retrieval."},
            {"Enhance Mobile App Navigation", "Improve mobile app navigation for easier access to features."},
            {"Implement Cross-Platform Syncing", "Enable syncing of data across different platforms used by the same user."},
            {"Upgrade Email Notification Templates", "Revise and update email notification templates for better communication."},
            {"Create Interactive User Polls", "Set up interactive user polls to gather opinions and preferences."},
            {"Implement Progressive Image Loading", "Load images progressively to improve page loading times."},
            {"Enhance Content Filtering Options", "Expand content filtering options to provide more tailored results to users."},
            {"Integrate Machine Learning Algorithms", "Incorporate machine learning algorithms for predictive analytics."},
            {"Optimize Database Replication", "Optimize database replication for improved data redundancy and availability."},
            {"Implement Smart Search Suggestions", "Provide smart search suggestions to assist users in finding relevant content."},
            {"Upgrade API Rate Limiting", "Enhance API rate limiting mechanisms to prevent abuse and ensure fair usage."},
            {"Create User Engagement Analytics", "Track user engagement metrics to understand user behavior."},
            {"Implement Customizable Themes", "Allow users to customize application themes according to their preferences."},
            {"Enhance Data Import/Export Tools", "Improve tools for importing and exporting data to and from the application."},
            {"Integrate Automated Data Cleansing", "Automate data cleansing processes to ensure data accuracy."},
            {"Upgrade Content Recommendation Engine", "Enhance the content recommendation engine for better accuracy and relevance."},
            {"Optimize SQL Queries", "Optimize SQL queries for faster database retrieval."},
            {"Enhance User Feedback Mechanism", "Improve the system for collecting and processing user feedback."},
            {"Implement Browser Push Notifications", "Enable browser-based push notifications for desktop users."},
            {"Upgrade Firewall Security", "Enhance firewall security to protect against cyber threats."},
            {"Create Community Forums", "Establish community forums for users to interact and share experiences."},
            {"Integrate Voice Recognition", "Incorporate voice recognition for hands-free interaction."},
            {"Improve Mobile App Accessibility", "Enhance accessibility features for the mobile app version."},
            {"Implement Social Login", "Allow users to log in using their social media accounts."},
            {"Upgrade Data Storage Solution", "Upgrade data storage solution for increased reliability and scalability."},
            {"Create Interactive Onboarding Process", "Develop an interactive onboarding process for new users."},
            {"Implement Augmented Reality Features", "Introduce augmented reality features for immersive experiences."},
            {"Upgrade CDN for Content Delivery", "Upgrade content delivery network for faster content distribution."},
            {"Enhance Error Reporting", "Improve error reporting mechanisms for faster issue resolution."},
            {"Implement User Rewards System", "Create a rewards system to incentivize user engagement."},
            {"Upgrade Cross-Platform Compatibility", "Ensure compatibility across various operating systems and devices."},
            {"Optimize Mobile App UI", "Optimize the user interface for better usability on mobile devices."},
            {"Implement Data Anonymization", "Anonymize user data to enhance privacy protection."},
            {"Upgrade API Documentation", "Improve documentation for developers integrating with the application's API."},
            {"Create Knowledge Base", "Establish a knowledge base for users to find answers to common questions."},
            {"Implement Load Balancing", "Introduce load balancing for better distribution of server resources."},
            {"Enhance Email Marketing Tools", "Improve tools for managing and analyzing email marketing campaigns."},
            {"Upgrade CAPTCHA Security", "Enhance CAPTCHA security to prevent spam and abuse."},
            {"Implement Continuous Integration", "Introduce continuous integration for automated code testing and deployment."},
    };

    private static final String LONG_STRING =
            """
                    aaa very long string aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                    hahahaha you though I was joking this string is reeeeaalllllyyyy loooooooooooooooooooooooooooooong and guess what
                    it ain't stopping dfsdkfbsfdkjsbdfkljdbflkjdsbflksdjbfskldjfblksdjbfklsjdbflkjsdbflkjsdbflksdbfjklsdfb
                    ok I'm done\n\n\n\n\n\n\nhaha just jk ok now I'm done
                    """;

    private final String[] PROJECT_NAMES = {
            "BlueSky Initiative",
            "Phoenix Rising",
            "InnovateX",
            "TigerEye Project",
            "SilverLining Endeavor",
            "Eclipse Evolution",
            "Zenith Quest",
            "Nebula Nexus",
            "Sapphire Sprint",
            "Lunar Horizon",
            "Starlight Stride",
            "Aurora Ascent",
            "Galactic Gateway",
            "Thunderbolt Taskforce",
            "Crimson Catalyst",
            "Velocity Venture",
            "Mystic Momentum",
            "Titan Triumph",
            "Ocean Odyssey",
            "Solar Serenity"
    };

}
