package voxel3d.physics;

import voxel3d.utility.Vector3I;
import voxel3d.utility.Vector3d;

public class AABB {
	
	private final double epsilon = 0.0001d;

    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    /**
     * Bounding box
     *
     * @param minX Minimum x side
     * @param minY Minimum y side
     * @param minZ Minimum z side
     * @param maxX Maximum x side
     * @param maxY Maximum y side
     * @param maxZ Maximum z side
     */
    public AABB() 
    {
    }
    
    public void set(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    /**
     * Copy the current bounding box object
     *
     * @return Clone of the bounding box
     */
    /*public AABB clone() {
        return new AABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }*/

    /**
     * Expand the bounding box. Positive and negative numbers controls which side of the box should grow.
     *
     * @param x Amount to expand the minX or maxX
     * @param y Amount to expand the minY or maxY
     * @param z Amount to expand the minZ or maxZ
     * @return The expanded bounding box
     */
    /*public AABB expand(double x, double y, double z) {
        double minX = this.minX;
        double minY = this.minY;
        double minZ = this.minZ;
        double maxX = this.maxX;
        double maxY = this.maxY;
        double maxZ = this.maxZ;

        // Handle expanding of min/max x
        if (x < 0.0F) {
            minX += x;
        } else {
            maxX += x;
        }

        // Handle expanding of min/max y
        if (y < 0.0F) {
            minY += y;
        } else {
            maxY += y;
        }

        // Handle expanding of min/max z
        if (z < 0.0F) {
            minZ += z;
        } else {
            maxZ += z;
        }

        // Create new bounding box
        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }*/

    /**
     * Expand the bounding box on both sides.
     * The center is always fixed when using grow.
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    /*public AABB grow(double x, double y, double z) {
        return new AABB(this.minX - x, this.minY - y,
                this.minZ - z, this.maxX + x,
                this.maxY + y, this.maxZ + z);
    }*/

    /**
     * Check for collision on the X axis
     *
     * @param otherBoundingBox The other bounding box that is colliding with the this one.
     * @param x                Position on the X axis that is colliding
     * @return Returns the corrected x position that collided.
     */
    public double clipXCollide(AABB otherBoundingBox, double x) {
        // Check if the boxes are colliding on the Y axis
        if (otherBoundingBox.maxY <= this.minY || otherBoundingBox.minY >= this.maxY) {
            return x;
        }

        // Check if the boxes are colliding on the Z axis
        if (otherBoundingBox.maxZ <= this.minZ || otherBoundingBox.minZ >= this.maxZ) {
            return x;
        }

        // Check for collision if the X axis of the current box is bigger
        if (x > 0.0F && otherBoundingBox.maxX <= this.minX) {
            double max = this.minX - otherBoundingBox.maxX - this.epsilon;
            if (max < x) {
                x = max;
            }
        }

        // Check for collision if the X axis of the current box is smaller
        if (x < 0.0F && otherBoundingBox.minX >= this.maxX) {
            double max = this.maxX - otherBoundingBox.minX + this.epsilon;
            if (max > x) {
                x = max;
            }
        }

        return x;
    }

    /**
     * Check for collision on the Y axis
     *
     * @param otherBoundingBox The other bounding box that is colliding with the this one.
     * @param y                Position on the X axis that is colliding
     * @return Returns the corrected x position that collided.
     */
    public double clipYCollide(AABB otherBoundingBox, double y) {
        // Check if the boxes are colliding on the X axis
        if (otherBoundingBox.maxX <= this.minX || otherBoundingBox.minX >= this.maxX) {
            return y;
        }

        // Check if the boxes are colliding on the Z axis
        if (otherBoundingBox.maxZ <= this.minZ || otherBoundingBox.minZ >= this.maxZ) {
            return y;
        }

        // Check for collision if the Y axis of the current box is bigger
        if (y > 0.0F && otherBoundingBox.maxY <= this.minY) {
            double max = this.minY - otherBoundingBox.maxY - this.epsilon;
            if (max < y) {
                y = max;
            }
        }

        // Check for collision if the Y axis of the current box is bigger
        if (y < 0.0F && otherBoundingBox.minY >= this.maxY) {
            double max = this.maxY - otherBoundingBox.minY + this.epsilon;
            if (max > y) {
                y = max;
            }
        }

        return y;
    }

    /**
     * Check for collision on the Y axis
     *
     * @param otherBoundingBox The other bounding box that is colliding with the this one.
     * @param z                Position on the X axis that is colliding
     * @return Returns the corrected x position that collided.
     */
    public double clipZCollide(AABB otherBoundingBox, double z) {
        // Check if the boxes are colliding on the X axis
        if (otherBoundingBox.maxX <= this.minX || otherBoundingBox.minX >= this.maxX) {
            return z;
        }

        // Check if the boxes are colliding on the Y axis
        if (otherBoundingBox.maxY <= this.minY || otherBoundingBox.minY >= this.maxY) {
            return z;
        }

        // Check for collision if the Z axis of the current box is bigger
        if (z > 0.0F && otherBoundingBox.maxZ <= this.minZ) {
            double max = this.minZ - otherBoundingBox.maxZ - this.epsilon;
            if (max < z) {
                z = max;
            }
        }

        // Check for collision if the Z axis of the current box is bigger
        if (z < 0.0F && otherBoundingBox.minZ >= this.maxZ) {
            double max = this.maxZ - otherBoundingBox.minZ + this.epsilon;
            if (max > z) {
                z = max;
            }
        }

        return z;
    }

    /**
     * Check if the two boxes are intersecting/overlapping
     *
     * @param otherBoundingBox The other bounding box that could intersect
     * @return The two boxes are overlapping
     */
    public boolean intersects(AABB otherBoundingBox) {
        // Check on X axis
        if (otherBoundingBox.maxX <= this.minX || otherBoundingBox.minX >= this.maxX) {
            return false;
        }

        // Check on Y axis
        if (otherBoundingBox.maxY <= this.minY || otherBoundingBox.minY >= this.maxY) {
            return false;
        }

        // Check on Z axis
        return (!(otherBoundingBox.maxZ <= this.minZ)) && (!(otherBoundingBox.minZ >= this.maxZ));
    }
    
    public boolean intersects(Ray r) 
    {
    	// r.dir is unit direction vector of ray
    	Vector3d dirfrac = new Vector3d();
    	dirfrac.x = 1.0 / r.direction.x;
    	dirfrac.y = 1.0 / r.direction.y;
    	dirfrac.z = 1.0 / r.direction.z;
    	// lb is the corner of AABB with minimal coordinates - left bottom, rt is maximal corner
    	// r.org is origin of ray
    	double t1 = (minX - r.start.x)*dirfrac.x;
    	double t2 = (maxX - r.start.x)*dirfrac.x;
    	double t3 = (minY - r.start.y)*dirfrac.y;
    	double t4 = (maxY - r.start.y)*dirfrac.y;
    	double t5 = (minZ - r.start.z)*dirfrac.z;
    	double t6 = (maxZ - r.start.z)*dirfrac.z;

    	double tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
    	double tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));
    	//double t = 0;
    	
    	// if tmax < 0, ray (line) is intersecting AABB, but the whole AABB is behind us
    	if (tmax < 0)
    	{
    	    //t = tmax;
    	    return false;
    	}

    	// if tmin > tmax, ray doesn't intersect AABB
    	if (tmin > tmax)
    	{
    	    //t = tmax;
    	    return false;
    	}

    	if(tmin > r.length)
    	{
    		//t = tmin;
    		return false;
    	}
    	
    	return true;
    }

    /**
     * Move the bounding box relative.
     *
     * @param x Relative offset x
     * @param y Relative offset y
     * @param z Relative offset z
     */
    /*public void move(double x, double y, double z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
    }*/

    /**
     * Create a new bounding box with the given offset
     *
     * @param x Relative offset x
     * @param y Relative offset x
     * @param z Relative offset x
     * @return New bounding box with the given offset relative to this bounding box
     */
    /*public AABB offset(double x, double y, double z) {
        return new AABB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }*/
    
    public void getNormal(double x, double y, double z, Vector3I writeback)
    {
    	byte direction = 0;
    	double lenght = Double.POSITIVE_INFINITY;
    	
    	if(maxX - x < lenght) {direction = 0; lenght = maxX - x;}
    	if(maxY - y < lenght) {direction = 1; lenght = maxY - y;}
    	if(maxZ - z < lenght) {direction = 2; lenght = maxZ - z;}
    	
    	if(x - minX < lenght) {direction = 3; lenght = x - minX;}
    	if(y - minY < lenght) {direction = 4; lenght = y - minY;}
    	if(z - minZ < lenght) {direction = 5; lenght = z - minZ;}
    	
    	if(direction == 0) {writeback.set(1, 0, 0);}
    	if(direction == 1) {writeback.set(0, 1, 0);}
    	if(direction == 2) {writeback.set(0, 0, 1);}
    	
    	if(direction == 3) {writeback.set(-1, 0, 0);}
    	if(direction == 4) {writeback.set(0, -1, 0);}
    	if(direction == 5) {writeback.set(0, 0, -1);}
    }

}
