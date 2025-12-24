package gitlet;

import java.io.Serializable;

import static gitlet.Repository.*;
import static gitlet.Utils.*;
import java.util.List;

/** Represent gitlet branch pointer object. */

public class Branch implements Serializable {

    /** The branch name. */
    private String name;

    /** The Head commit id.*/
    private String HEAD;

    public Branch(String name, String head) {
        if (isExists(name)) {
            throw new GitletException("Branch already exists");
        }
        this.name = name;
        this.HEAD = head;
    }

    /** Determine whether the branch name exists. */
    public boolean isExists(String name) {
        name = correctName(name);
        List<String> names = plainFilenamesIn(BRANCHES_DIR);
        return name != null && names.contains(name);
    }


    public String correctName(String name) {
        return name.replace("/", "_");
    }

    public String getName() {
        return name;
    }

    public String getHEAD() {
        return HEAD;
    }
}
