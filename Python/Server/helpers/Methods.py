class Methods:

    @staticmethod
    def is_subset(A: [], B: []) -> bool:
        """
            Checks if array b is in array a
            :param A: an array of elements
            :param B: an array of elements
            :return: true if B is a subset of A
        """

        def isSubArray(n, m):
            i = 0
            j = 0

            while i < n and j < m:

                if A[i] == B[j]:
                    i += 1
                    j += 1

                    if j == m:
                        return True

                else:
                    i = i - j + 1
                    j = 0

            return False

        return isSubArray(len(A), len(B))
