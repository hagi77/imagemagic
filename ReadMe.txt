IMAGE MAGIC APP

--- Project architecture:

The project uses the MVVM architecture with UseCases, which are an element of the Clean approach.


--- User-drawn path data structure

I use three classes to store and manipulate the user-generated path data:

- The Node class keeps the coordinates (relative to the scaled-down preview image being displayed)
of motion events and a reference to the next node. A series of nodes, therefore, forms a tree-like
structure.

- The PathModel class stores data about one path: the path color, stroke width as well as first and
last Node. The PathModel class can add a Node (if it's coordinates are not too close to the
current last node's).

This class can also "flatten" the nodes tree and return it in a List of Nodes (which may be used for
implementing a logic for serializing and storing) or as a List of Android Paths which are used
for drawing the Path onto a Canvas.

- The Paths class. Its responsibility is to keep a list of PathModel objects and returning Path
data ready for applying to the Canvas. The Paths class can also scale the Path so that it
may be drawn on a bigger Canvas (ie. when saving the user-drawn to the output bitmap).


--- Potential improvements:

To avoid potential out of memory errors while applying the user-drawn path to the output bitmap,
the bitmap should be split to parts (eg. using the BitmapRegionDecoder) and processed sequentially.
