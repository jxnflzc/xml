package per.jxnflzc.utils;

public class Version {
    private int major = 0;
    private int minor = 0;
    private int build = 0;

    public Version(){
    }

    public Version(int major, int minor, int build){
        this.major = major;
        this.minor = minor;
        this.build = build;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getBuild() {
        return build;
    }

    @Override
    public String toString() {
        return "V " + major + "." + minor + "." + build;
    }
}
