package com.tradespace.tradebid.adapters.db;

import com.tradespace.tradebid.domain.model.User;
import com.tradespace.tradebid.domain.model.UserDetails;
import com.tradespace.tradebid.domain.model.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
@Entity(name = "User")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "userId"),
                @UniqueConstraint(columnNames = "userProfileName"),
                @UniqueConstraint(columnNames = "userName"),
        }
)
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Type(type = "uuid-char")
    @NotNull
    private UUID userId;

    @NotNull
    private String userProfileName;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private UserType userType;

    @NotNull
    private String userName;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @OneToMany(mappedBy = "poster", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectEntity> projects;

    public UserEntity(UUID userId, String userProfileName, UserType userType, String userName, String firstName, String lastName, List<ProjectEntity> projects) {
        this.userId = userId;
        this.userProfileName = userProfileName;
        this.userType = userType;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.projects = projects;
    }

    public User toUser() {
        return new User(
                userId,
                userProfileName,
                userType,
                userName,
                firstName,
                lastName
        );
    }

    public UserDetails toUserDetails() {
        return new UserDetails(
                userId,
                userProfileName,
                userType,
                userName,
                firstName,
                lastName,
                projects != null ? projects.stream().map(ProjectEntity::toProject).collect(toList()) : emptyList()
        );
    }

    public static UserEntity fromUser(User user) {
        if (user == null) {
            return null;
        }
        return new UserEntity(
                user.getUserId(),
                user.getUserProfileName(),
                user.getUserType(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                emptyList()
        );
    }

}
