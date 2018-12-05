class Vector {

    int x;
    int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector)) {
            return false;
        } else {
            Vector other = (Vector)obj;
            return this.x == other.x && this.y == other.y;
        }
    }
}
