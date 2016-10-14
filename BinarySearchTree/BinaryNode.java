
// Basic node stored in unbalanced binary search trees
// Note that this class is not accessible outside
// of this package.

class BinaryNode {
    // Constructors
    BinaryNode( Comparable theElement ) {
        element = theElement;
        left = right = null;
    }
    
    // Friendly data; accessible by other package routines
    Comparable element;      // The data in the node
    BinaryNode left;         // Left child
    BinaryNode right;        // Right child
}