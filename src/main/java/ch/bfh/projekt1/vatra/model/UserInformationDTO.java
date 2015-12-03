package ch.bfh.projekt1.vatra.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 03.12.15.
 */
public class UserInformationDTO {

    public class UserAppInformation {
        private String id;
        private String name;

        public UserAppInformation(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private String email;
    private List<UserAppInformation> apps = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addAppInformation(String id, String name) {
        apps.add(new UserAppInformation(id, name));
    }

    public List<UserAppInformation> getApps() {
        return apps;
    }
}
