package DTO;

public class AccountDTO {
    
    private String username;
    private String password;
    private String roleGroupId;
    private String avatarImg;
    
    public AccountDTO(){
        this.username = "";
        this.password = "";
        this.roleGroupId = "";
        this.avatarImg = "";
    }
    
    public AccountDTO(String username, String password, String roleGroupId, String avatarImg){
        this.username = username;
        this.password = password;
        this.avatarImg = avatarImg;
        this.roleGroupId = roleGroupId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }
    
    public String getRoleGroupId() {
        return this.roleGroupId;
    }
    
    public void setRoleGroupId(String roleGroupId) {
        this.roleGroupId = roleGroupId;
    }
}
