'''
226. Invert binary tree

DESCRIPTION:
Given the root of a binary tree, invert the tree, and return its root.
'''
from typing import Optional

# Definition for a binary tree node.
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

'''
Base case: if the current node is null, return null.
Use a temporary variable to swap the left and right nodes.
Then, use DFS post-order to recursively go through all the nodes in the binary tree.

Time: O(n) - iterating through all nodes in the binary tree
Space: O(n) - recursive call stack
'''
class Solution:
    def invertTree(self, root: Optional[TreeNode]) -> Optional[TreeNode]:
        if not root:
            return None

        temp = root.left
        root.left = root.right
        root.right = temp

        # DFS post-order
        self.invertTree(root.left)
        self.invertTree(root.right)
        return root
    
'''
Test cases:
root = [4,2,7,1,3,6,9]
root = [2,1,3]
root = []
'''