package DTO;

public class RoleGroupDTO {
    private String roleGroupId;
    private String roleGroupName;
    private boolean[] functionsArray;
    
    public RoleGroupDTO() {
        this.roleGroupId = "";
        this.roleGroupName = "";
        this.functionsArray = null;
    }
    
    public RoleGroupDTO(String roleGroupId, String roleGroupName, boolean[] functionsArray) {
        this.roleGroupId = roleGroupId;
        this.roleGroupName = roleGroupName;
        this.functionsArray = functionsArray;
    }
    
    public String getRoleGroupId() {
        return roleGroupId;
    }
    
    public void setRoleGroupId(String roleGroupId) {
        this.roleGroupId = roleGroupId;
    }
    
    public String getRoleGroupName() {
        return roleGroupName;
    }
    
    public void setRoleGroupName(String roleGroupName) {
        this.roleGroupName = roleGroupName;
    }
    
    public boolean[] getFunctionsArray() {
        return functionsArray;
    }
    
    public void setFunctionsArray(boolean[] functionsArray) {
        this.functionsArray = functionsArray;
    }
}