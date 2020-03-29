package per.jxnflzc.utils;


public interface Versionable {
    public static final int MAJOR = 0;
    public static final int MINOR = 0;
    public static final int BUILD = 0;

    public int getMajor();

    public int getMinor();

    public int getBuild();

    public String getVersion();

    public void setMajor(int major);

    public void seMinor(int minor);

    public void seBuild(int build);

    public void setVersion(int major, int minor, int build);
}
