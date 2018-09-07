package audi.jenganative.math;

/**
 * Edited by audi on 1-8-2018.
 */
//made by: Robert Sedgewick and Kevin Wayne
//link: https://introcs.cs.princeton.edu/java/32class/Quaternion.java.html
import android.renderscript.Matrix4f;

import static java.lang.Math.asin;
import static java.lang.Math.atan2;

public class Quaternion {
    public float x, y, z, w;

    // create a new object with the given components
    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    // return a string representation of the invoking object
    public String toString() {
        return x + " + " + y + "i + " + z + "j + " + w + "k";
    }

    // return the quaternion norm
    public float length() {
        return (float)Math.sqrt(x * x + y * y + z * z + w * w);
    }

    // return the quaternion conjugate
    public Quaternion conjugate() {
        return new Quaternion(x, -y, -z, -w);
    }

    // return a new Quaternion whose value is (this + b)
    public Quaternion add(Quaternion b) {
        Quaternion a = this;
        return new Quaternion(a.x +b.x, a.y +b.y, a.z +b.z, a.w +b.w);
    }


    // return a new Quaternion whose value is (this * b)
    public Quaternion mul(Quaternion b) {
        Quaternion a = this;
        float y0 = a.x *b.x - a.y *b.y - a.z *b.z - a.w *b.w;
        float y1 = a.x *b.y + a.y *b.x + a.z *b.w - a.w *b.z;
        float y2 = a.x *b.z - a.y *b.w + a.z *b.x + a.w *b.y;
        float y3 = a.x *b.w + a.y *b.z - a.z *b.y + a.w *b.x;
        return new Quaternion(y0, y1, y2, y3);
    }

    // return a new Quaternion whose value is the inverse of this
    public Quaternion inverse() {
        float d = x * x + y * y + z * z + w * w;
        return new Quaternion(x /d, -y /d, -z /d, -w /d);
    }


    // return a / b
    // we use the definition a * b^-1 (as opposed to b^-1 a)
    public Quaternion div(Quaternion b) {
        Quaternion a = this;
        return a.mul(b.inverse());
    }

    public void toMatrix(Matrix4f out){
        Quaternion q = this;
        float[] m = out.getArray();

        // Precalculate coordinate products
        float x = q.x * 2.0F;
        float y = q.y * 2.0F;
        float z = q.z * 2.0F;
        float xx = q.x * x;
        float yy = q.y * y;
        float zz = q.z * z;
        float xy = q.x * y;
        float xz = q.x * z;
        float yz = q.y * z;
        float wx = q.w * x;
        float wy = q.w * y;
        float wz = q.w * z;

        m[0] = 1.0f - (yy + zz);    m[4] = xy + wz;            m[8] = xz - wy;            m[12] = 0.0F;
        m[1] = xy - wz;             m[5] = 1.0f - (xx + zz);   m[9] = yz + wx;            m[13] = 0.0F;
        m[2] = xz + wy;             m[6] = yz - wx;            m[10] = 1.0f - (xx + yy);   m[14] = 0.0F;
        m[3] = 0.0F;                m[7] = 0.0F;               m[11] = 0.0F;               m[15] = 1.0F;
    }

    public static Quaternion identity(){
        return new Quaternion(0, 0, 0, 1);
    }

    public Quaternion setEulerAngles (float yaw, float pitch, float roll) {
        return setEulerAnglesRad(
                yaw * MathUtil.degreesToRadians,
                pitch * MathUtil.degreesToRadians,
                roll * MathUtil.degreesToRadians);
    }

    public Quaternion setEulerAnglesRad (float yaw, float pitch, float roll) {
        final float hr = roll * 0.5f;
        final float shr = (float)Math.sin(hr);
        final float chr = (float)Math.cos(hr);
        final float hp = pitch * 0.5f;
        final float shp = (float)Math.sin(hp);
        final float chp = (float)Math.cos(hp);
        final float hy = yaw * 0.5f;
        final float shy = (float)Math.sin(hy);
        final float chy = (float)Math.cos(hy);
        final float chy_shp = chy * shp;
        final float shy_chp = shy * chp;
        final float chy_chp = chy * chp;
        final float shy_shp = shy * shp;

        x = (chy_shp * chr) + (shy_chp * shr); // cos(yaw/2) * sin(pitch/2) * cos(roll/2) + sin(yaw/2) * cos(pitch/2) * sin(roll/2)
        y = (shy_chp * chr) - (chy_shp * shr); // sin(yaw/2) * cos(pitch/2) * cos(roll/2) - cos(yaw/2) * sin(pitch/2) * sin(roll/2)
        z = (chy_chp * shr) - (shy_shp * chr); // cos(yaw/2) * cos(pitch/2) * sin(roll/2) - sin(yaw/2) * sin(pitch/2) * cos(roll/2)
        w = (chy_chp * chr) + (shy_shp * shr); // cos(yaw/2) * cos(pitch/2) * cos(roll/2) + sin(yaw/2) * sin(pitch/2) * sin(roll/2)
        return this;
    }

    public Vector3 toEulerAngle() {
        Vector3 eulers = new Vector3();

        // roll (x-axis rotation)
        double sinr = +2.0 * (w * x + y * z);
        double cosr = +1.0 - 2.0 * (x * x + y * y);
        eulers.x = (float)atan2(sinr, cosr);

        // pitch (y-axis rotation)
        double sinp = +2.0 * (w * y - z * x);
        if (Math.abs(sinp) >= 1)
            eulers.y = (float)Math.copySign(Math.PI / 2, sinp); // use 90 degrees if out of range
        else
            eulers.y = (float)asin(sinp);

        // yaw (z-axis rotation)
        double siny = +2.0 * (w * z + x * y);
        double cosy = +1.0 - 2.0 * (y * y + z * z);
        eulers.z = (float)atan2(siny, cosy);
        return eulers;
    }

    public static Quaternion eulerAngles(float x, float y, float z){
        Quaternion q = Quaternion.identity();
        q.setEulerAngles(x, y, z);
        return q;
    }

    public Quaternion copy(){
        return new Quaternion(x, y, z, w);
    }
}
