package repo.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 200, nullable = false)
    private String path;

    @Column(length = 50)
    private String name;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Date creationDate;

    @OneToMany(mappedBy = "directory", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<File> files = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "parent_directory_id")
    private Directory parentDirectory;

    @OneToMany(mappedBy = "parentDirectory", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Directory> subdirectories = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Directory)) return false;
        Directory directory = (Directory) o;
        return getId() == directory.getId() &&
                Objects.equals(getPath(), directory.getPath()) &&
                Objects.equals(getName(), directory.getName()) &&
                Objects.equals(getCreationDate(), directory.getCreationDate()) &&
                Objects.equals(files, directory.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPath(), getName(), getCreationDate(), files);
    }

    // region Properties

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(final Set<File> files) {
        this.files = files;
    }

    public Set<Directory> getSubdirectories() {
        return subdirectories;
    }

    public void setSubdirectories(final Set<Directory> subdirectories) {
        this.subdirectories = subdirectories;
    }

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public void setParentDirectory(final Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
        parentDirectory.subdirectories.add(this);
    }

    //endregion
}
