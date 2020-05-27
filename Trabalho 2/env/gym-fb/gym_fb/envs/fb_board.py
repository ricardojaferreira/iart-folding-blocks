import math


def TextBGColor(color, text):
    switcher = {
        "end": "\033[0m",
        "red": "\u001b[41m",
        "green": "\u001b[42m",
        "yellow": "\u001b[43m",
        "blue": "\u001b[44m",
        "magenta": "\u001b[45m",
        "cyan": "\u001b[46m",
        "black": "\u001b[40m",
        "white": "\u001b[47m"
    }
    return switcher.get(color, "\033[0m") + text + switcher.get("end")


class fb_position:
    def __init__(self, i, j, centerCoord):
        self.arrayPos = [i, j]
        x = j - centerCoord[0]
        y = centerCoord[1] - i
        self.coord = [x, y]


class fb_cell:
    def __init__(self, i, j, centerCoord, id):
        self.positions = []
        self.centerCoord = centerCoord
        self.positions.append(fb_position(i, j, centerCoord))
        self.centroid = [self.positions[0].coord[0],
                         self.positions[0].coord[1]]
        self.width = 1
        self.height = 1
        self.id = id

        switcher = {
            0: "black",
            1: "white",
            2: "red",
            3: "green",
            4: "yellow",
            5: "blue",
            6: "magenta",
            7: "cyan"
        }
        self.color = switcher.get(self.id % 7, "cyan")
        self.chr = TextBGColor(self.color, ' ')

    def isBlock(self):
        return self.id > 1

    def getAdjancentPos(self, blockPos):
        for pos in self.positions:
            if (pos.arrayPos[0] == blockPos[0]):
                if abs(pos.arrayPos[1] - blockPos[1]) == 1:
                    return pos
            elif (pos.arrayPos[1] == blockPos[1]):
                if abs(pos.arrayPos[0] - blockPos[0]) == 1:
                    return pos
        return None

    def hasRow(self, value):
        for pos in self.positions:
            if pos.arrayPos[0] == value:
                return True
        return False

    def hasCol(self, value):
        for pos in self.positions:
            if pos.arrayPos[1] == value:
                return True
        return False

    def addAdjacent(self, blockPos):
        if not self.hasRow(blockPos[0]):
            self.height += 1

        if not self.hasCol(blockPos[1]):
            self.width += 1

        fbpos = fb_position(blockPos[0], blockPos[1], self.centerCoord)
        self.positions.append(fbpos)
        self.centroid = self.calculateCentroid()
        return True

    def calculateCentroid(self):
        xPos = []
        yPos = []
        for pos in self.positions:
            if not (pos.coord[0] in xPos):
                xPos.append(pos.coord[0])
            if not (pos.coord[1] in yPos):
                yPos.append(pos.coord[1])

        centerX = sum(xPos) / len(xPos)
        centerY = sum(yPos) / len(yPos)
        return [centerX, centerY]

    def getVerticalFlip(self):
        res = []
        for pos in self.positions:
            x = pos.coord[0]
            y = pos.coord[1]
            x -= self.centroid[0]
            y -= self.centroid[1]
            s = round(math.sin(math.pi), 4)
            c = round(math.cos(math.pi), 4)
            rx = x * c - y * s
            ry = x * s + y * c
            rx *= -1
            rx += self.centroid[0]
            ry += self.centroid[1]
            resx = rx + self.centerCoord[0]
            resy = self.centerCoord[1] - ry
            res.append([resy, resx])
        return res

    def getMirrorFlip(self):
        res = []
        for pos in self.positions:
            x = pos.coord[0]
            x -= self.centroid[0]
            x *= -1
            x += self.centroid[0]
            resx = x + self.centerCoord[0]
            res.append([pos.arrayPos[0], resx])
        return res

    def dupVert(self, pos):
        self.height *= 2
        for position in pos:
            self.positions.append(fb_position(
                position[0], position[1], self.centerCoord))
        self.centroid = self.calculateCentroid()

    def dupHor(self, pos):
        self.width *= 2
        for position in pos:
            self.positions.append(fb_position(
                position[0], position[1], self.centerCoord))
        self.centroid = self.calculateCentroid()


class fb_board:
    def __init__(self, board):
        self.height = len(board)
        self.width = len(board[0])
        self.centerCoord = [self.width // 2, self.height // 2]
        self.cells = self.initBoard()
        self.blocks = []
        self.setupBoard(board)
        self.blockCount = len(self.blocks)

    def initBoard(self):
        return [[fb_cell(i, j, self.centerCoord, 0) for j in range(self.width)]
                for i in range(self.height)]

    def setupBoard(self, board):
        for i in range(self.height):
            for j in range(self.width):
                blc = board[i][j]
                if blc != 0:
                    if blc == 1:
                        self.cells[i][j] = fb_cell(i, j, self.centerCoord, blc)
                        continue
                    block = self.getBlock(blc)
                    if block is None:
                        self.cells[i][j] = fb_cell(i, j, self.centerCoord, blc)
                        self.blocks.append(self.cells[i][j])
                    else:
                        block.addAdjacent([i, j])
                        self.cells[i][j] = block

    def getBlock(self, id):
        for block in self.blocks:
            if block.id == id:
                return block
        return None

    def verifyAvailablePos(self, i, j):
        if i < 0 or i >= self.height:
            return False
        if j < 0 or j >= self.width:
            return False
        if self.cells[i][j].id != 1:
            return False
        return True

    def duplicateBlockVert(self, block, up):
        flip = block.getVerticalFlip()
        for pos in flip:
            i = int(pos[0])
            i += -block.height if up else block.height
            j = int(pos[1])
            if not self.verifyAvailablePos(i, j):
                return False

        for pos in flip:
            pos[0] = int(pos[0])
            pos[0] += -block.height if up else block.height
            pos[1] = int(pos[1])
            self.cells[pos[0]][pos[1]] = block

        block.dupVert(flip)
        return True

    def duplicateBlockHor(self, block, right):
        mirror = block.getMirrorFlip()
        for pos in mirror:
            i = int(pos[0])
            j = int(pos[1])
            j += block.width if right else -block.width
            if not self.verifyAvailablePos(i, j):
                return False

        for pos in mirror:
            pos[0] = int(pos[0])
            pos[1] = int(pos[1])
            pos[1] += block.width if right else -block.width
            self.cells[pos[0]][pos[1]] = block

        block.dupHor(mirror)
        return True

    def duplicateBlock(self, id, dir):
        block = self.getBlock(id)

        if dir == "DBUP":
            return self.duplicateBlockVert(block, True)
        if dir == "DBDOWN":
            return self.duplicateBlockVert(block, False)
        if dir == "DBRIGHT":
            return self.duplicateBlockHor(block, True)
        if dir == "DBLEFT":
            return self.duplicateBlockHor(block, False)

    def print(self):
        for i in range(self.height):
            print()
            for j in range(self.width):
                cell = self.cells[i][j]
                print(cell.chr, end="")
