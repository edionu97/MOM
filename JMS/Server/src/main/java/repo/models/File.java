package repo.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Objects;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 200, nullable = false)
    private String path;

    @Column(length = 50)
    private String name;

    @Lob
    @Column(name = "binary_content", columnDefinition = "LONGBLOB")
    private Blob binaryContent;

    @Column(name = "text_content", length = 5000)
    private String textContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "directory_id")
    private Directory directory;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File)) return false;
        File file = (File) o;
        return getId() == file.getId() &&
                Objects.equals(getPath(), file.getPath()) &&
                Objects.equals(getName(), file.getName()) &&
                Objects.equals(getBinaryContent(), file.getBinaryContent()) &&
                Objects.equals(getTextContent(), file.getTextContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPath(), getName(), getBinaryContent(), getTextContent());
    }

    //region Properties

    public void setId(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public Blob getBinaryContent() {
        return binaryContent;
    }

    public void setBinaryContent(final Blob binaryContent) {
        this.binaryContent = binaryContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(final String textContent) {
        this.textContent = textContent;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(final Directory directory) {
        this.directory = directory;
        directory.getFiles().add(this);
    }

    //endregion
}
