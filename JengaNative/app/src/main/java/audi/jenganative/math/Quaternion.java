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

    /*public Quaternion setEulerAngles (float yaw, float pitch, float roll) {
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

    public Vector3 toEulerAngles() {
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
    }*/

    public static Quaternion eulerAngles(float x, float y, float z){
        Quaternion q = Quaternion.identity();
        q.setEulerAngles(x, y, z);
        return q;
    }

    public Quaternion copy(){
        return new Quaternion(x, y, z, w);
    }

    public static Vector3 multiplyVector(Quaternion rotation, Vector3 pos) {
        float x = rotation.x * 2f;
        float y = rotation.y * 2f;
        float z = rotation.z * 2f;
        float xx = rotation.x * x;
        float yy = rotation.y * y;
        float zz = rotation.z * z;
        float xy = rotation.x * y;
        float xz = rotation.x * z;
        float yz = rotation.y * z;
        float wx = rotation.w * x;
        float wy = rotation.w * y;
        float wz = rotation.w * z;

        Vector3 result = new Vector3(
                (1f - (yy + zz)) * pos.x + (xy - wz) * pos.y + (xz + wy) * pos.z,
                (xy + wz) * pos.x + (1f - (xx + zz)) * pos.y + (yz - wx) * pos.z,
                (xz - wy) * pos.x + (yz + wx) * pos.y + (1f - (xx + yy)) * pos.z
        );
        return result;
    }

    //source link: https://answers.unity.com/questions/467614/what-is-the-source-code-of-quaternionlookrotation.html
    public static Quaternion lookRotation(Vector3 forward, Vector3 up) {
        Vector3 vector = forward.normal();
        Vector3 vector2 = (Vector3.cross(up, vector)).normal();
        Vector3 vector3 = Vector3.cross(vector, vector2);

        float m00 = vector2.x;
        float m01 = vector2.y;
        float m02 = vector2.z;
        float m10 = vector3.x;
        float m11 = vector3.y;
        float m12 = vector3.z;
        float m20 = vector.x;
        float m21 = vector.y;
        float m22 = vector.z;


        float num8 = (m00 + m11) + m22;
        Quaternion quaternion = new Quaternion(0,0,0,0);
        if (num8 > 0f)
        {
            float num = (float)Math.sqrt(num8 + 1f);
            quaternion.w = num * 0.5f;
            num = 0.5f / num;
            quaternion.x = (m12 - m21) * num;
            quaternion.y = (m20 - m02) * num;
            quaternion.z = (m01 - m10) * num;
            return quaternion;
        }
        if ((m00 >= m11) && (m00 >= m22))
        {
            float num7 = (float)Math.sqrt(((1f + m00) - m11) - m22);
            float num4 = 0.5f / num7;
            quaternion.x = 0.5f * num7;
            quaternion.y = (m01 + m10) * num4;
            quaternion.z = (m02 + m20) * num4;
            quaternion.w = (m12 - m21) * num4;
            return quaternion;
        }
        if (m11 > m22)
        {
            float num6 = (float)Math.sqrt(((1f + m11) - m00) - m22);
            float num3 = 0.5f / num6;
            quaternion.x = (m10+ m01) * num3;
            quaternion.y = 0.5f * num6;
            quaternion.z = (m21 + m12) * num3;
            quaternion.w = (m20 - m02) * num3;
            return quaternion;
        }
        float num5 = (float)Math.sqrt(((1f + m22) - m00) - m11);
        float num2 = 0.5f / num5;
        quaternion.x = (m20 + m02) * num2;
        quaternion.y = (m21 + m12) * num2;
        quaternion.z = 0.5f * num5;
        quaternion.w = (m01 - m10) * num2;
        return quaternion;
    }

    //TEMP

    public Quaternion setEulerAngles(float xAngle, float yAngle, float zAngle) {
        xAngle *= MathUtil.degreesToRadians;
        yAngle *= MathUtil.degreesToRadians;
        zAngle *= MathUtil.degreesToRadians;

        float angle;
        float sinY, sinZ, sinX, cosY, cosZ, cosX;
        angle = zAngle * 0.5f;
        sinZ = (float)Math.sin(angle);
        cosZ = (float)Math.cos(angle);
        angle = yAngle * 0.5f;
        sinY = (float)Math.sin(angle);
        cosY = (float)Math.cos(angle);
        angle = xAngle * 0.5f;
        sinX = (float)Math.sin(angle);
        cosX = (float)Math.cos(angle);

        // variables used to reduce multiplication calls.
        float cosYXcosZ = cosY * cosZ;
        float sinYXsinZ = sinY * sinZ;
        float cosYXsinZ = cosY * sinZ;
        float sinYXcosZ = sinY * cosZ;

        w = (cosYXcosZ * cosX - sinYXsinZ * sinX);
        x = (cosYXcosZ * sinX + sinYXsinZ * cosX);
        y = (sinYXcosZ * cosX + cosYXsinZ * sinX);
        z = (cosYXsinZ * cosX - sinYXcosZ * sinX);

        //normalizeLocal();
        return this;
    }

    public Vector3 toEulerAngles() {
        float[] angles = {0,0,0};

        float sqw = w * w;
        float sqx = x * x;
        float sqy = y * y;
        float sqz = z * z;
        float unit = sqx + sqy + sqz + sqw; // if normalized is one, otherwise
        // is correction factor
        float test = x * y + z * w;
        if (test > 0.499 * unit) { // singularity at north pole
            angles[1] = 2 * (float)Math.atan2(x, w);
            angles[2] = (float)Math.PI / 2;
            angles[0] = 0;
        } else if (test < -0.499 * unit) { // singularity at south pole
            angles[1] = -2 * (float)Math.atan2(x, w);
            angles[2] = -(float)Math.PI / 2;
            angles[0] = 0;
        } else {
            angles[1] = (float)Math.atan2(2 * y * w - 2 * x * z, sqx - sqy - sqz + sqw); // yaw or heading
            angles[2] = (float)Math.asin(2 * test / unit); // roll or bank
            angles[0] = (float)Math.atan2(2 * x * w - 2 * y * z, -sqx + sqy - sqz + sqw); // pitch or attitude
        }

        for(int i = 0; i < angles.length; i++){
            angles[i] = angles[i] * MathUtil.radiansToDegrees;
        }
        return new Vector3(angles[0], angles[1], angles[2]);
    }


}
