package ch.bfh.projekt1.vatra.model;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 03.12.15.
 */
public class UserInformationDTO {

    public class UserAppInformation {

        @Nonnull
        private String id;

        @Nonnull
        private String name;

        public UserAppInformation(@Nonnull String id, @Nonnull String name) {
            this.id = id;
            this.name = name;
        }

        @Nonnull
        public String getId() {
            return id;
        }

        public void setId(@Nonnull String id) {
            this.id = id;
        }

        @Nonnull
        public String getName() {
            return name;
        }

        public void setName(@Nonnull String name) {
            this.name = name;
        }
    }

    @Nonnull
    private String email;

    @Nonnull
    private List<UserAppInformation> apps = new ArrayList<>();

    @Nonnull
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nonnull String email) {
        this.email = email;
    }

    public void addAppInformation(@Nonnull String id, @Nonnull String name) {
        apps.add(new UserAppInformation(id, name));
    }

    @Nonnull
    public List<UserAppInformation> getApps() {
        return apps;
    }
}
