# LinkedList

- Linear data structure, similar to arrays
- Each element in linked list is a seperate object while all the objects are linked togather by the reference field.
- The idea is to drop the continous memory requirements so that insertions, deletions can effeciently happen at the
  middle also and no need to pre-allocate the space.
- In LinkedList, we do not have to specify the size of the list as a linked list is dynamic data structure and it
  automatically changes size when an element is added or removed.
- 3 types (Singly LinkedList, Doubly LinkedList, Circular LinkedList)

## Singly LinkedList: (SLL)

- A singly linked list is a data structure made up of nodes, where:
- Each node contains data (a value)
- Each node also has a pointer (reference) to the next node in the list
- The last node points to null

## Implementation of LinkedList:

- Each node object stores two things, one is the data (value) and the second is the memory location of the next or
  previous or both next and previous object address.
- Also, the nodes of the linked list are not stored in the contiguous memory location, they are linked to each other
  with the help of next and previous pointers.

## LinkedList Features:

- A Linked list provides us the ability to use the non-contiguous memory locations as a contiguous memeory location.
- It is dynamic data structure, hence there is no need to pre-allocate the memory. This results in efficient utilization
  of the memory.
- Also unlike the dynamic array, Insertion and Deletion at the ends of the linked list are performed in constant time.
- Concatenation of two linked lists is much more efficient in terms of time and space.

## Doubly LinkedList (DLL)

- Doubly LinkedList can be traversed in both ways head to tail and tail to head. For this, each object stores the data
  and memory location of the next object as well as the memory location of previous object.
- 