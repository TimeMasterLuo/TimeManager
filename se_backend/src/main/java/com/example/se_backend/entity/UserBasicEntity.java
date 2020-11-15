package com.example.se_backend.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "User_basic", schema = "SE-DB", catalog = "")
public class UserBasicEntity {
    private Integer id;
    private String name;
    private String pw;
    private String email;
    private String phone;



    /**
     * 1、配置用户到角色的多对多关系
     *      配置多对多的映射关系
     *          1.声明表关系的配置
     *              @ManyToMany(targetEntity:对方实体类字节码)   多对多
     *          2.配置中间表（包含两个外键）
     *              @JoinTable
     *                  name：中间表名称
     *                  joinColumns：配置当前对象在中间被的外键
     *                      JoinColumn：{数组}
     *                          name：当前表的外键的名字（可随便取）
     *                          referencedColumnName：参照主表（该表）的主键
     *                  inverseJoinColumns：配置对方对象在中间表的外键
     *
     */
    @ManyToMany(targetEntity = UserBasicEntity.class)
    @JoinTable(name = "Friend_List",
            // joinColumns,当前对象在中间表中的外键,
            // name="当前表的外键的名字（可随便取）“
            // referencedColumnName=“参照该表的主键”
            joinColumns = {@JoinColumn(name = "my_id",referencedColumnName = "id")},
            //inverseJoinColumns :对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "friend_id",referencedColumnName = "id")}
    )
    private List<UserBasicEntity> friends = (List<UserBasicEntity>) new HashSet<UserBasicEntity>(0);

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "pw", nullable = true, length = 255)
    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 255)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBasicEntity that = (UserBasicEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(pw, that.pw) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pw, email, phone);
    }
}
