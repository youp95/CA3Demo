package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "username", length = 25)
  private String userName;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "password")
  private String password;
  @JoinTable(
          name = "roles_assigned",
          joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
  )
  
  @ManyToMany
  private List<Role> roleList;


// gensalt's log_rounds parameter determines the complexity
// the work factor is 2**log_rounds, and the default is 10
  

  public List<String> getRolesAsStrings() {
    if (roleList.isEmpty()) {
      return null;
    }
    List<String> rolesAsStrings = new ArrayList();
    for (Role role : roleList) {
      rolesAsStrings.add(role.getRoleName());
    }
    return rolesAsStrings;
  }

  public User() {}

  //TODO Change when password is hashed
  // Hash a password for the first time


// Check that an unencrypted password matches one that has
// previously been hashed

        
   public boolean verifyPassword(String pw){
        return (BCrypt.checkpw(pw, password));
       
    }

  public User(String userName, String password) {
    this.userName = userName;
    String hashed1 = BCrypt.hashpw(password, BCrypt.gensalt(12));
    this.password = hashed1;
    roleList = new ArrayList<>();
   
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Role> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<Role> roleList) {
    this.roleList = roleList;
  }

  public void addRole(Role userRole) {
    roleList.add(userRole);
  }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
  
  

}
