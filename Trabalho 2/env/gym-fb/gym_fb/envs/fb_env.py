import random
import gym
from gym import error, spaces, utils
from gym.utils import seeding
from gym_fb.envs.fb_board import fb_board
import numpy as np

boardLayout1 = [
    [1, 1, 1],
    [1, 1, 1],
    [1, 1, 4]
]

boardLayout2 = [
    [4, 4, 1, 1],
    [1, 1, 1, 1],
    [1, 1, 2, 2],
    [1, 1, 1, 1],
    [1, 1, 1, 1],
    [1, 1, 3, 3]
]

boardLayout3 = [
    [1, 1, 4, 0, 0],
    [1, 1, 4, 0, 0],
    [1, 1, 4, 0, 0],
    [1, 1, 1, 0, 0],
    [1, 1, 1, 0, 0],
    [1, 1, 1, 0, 0],
    [1, 1, 1, 1, 1],
    [2, 1, 1, 1, 5]
]


class FoldingBlocks(gym.Env):
    metadata = {'render.modes': ['human']}

    def __init__(self):
        self.actions = ["DBUP", "DBDOWN", "DBLEFT", "DBRIGHT"]
        self.boardLayout = boardLayout2
        self.board = fb_board(self.boardLayout)
        self.stateCount = 1
        self.stateDict = {self.board.hashCode(): 0}
        self.state = 0
        self.done = False
        self.invalidMove = False
        self.reward = 0
        self.action_space = spaces.Discrete(self.board.blockCount * 4-1)

    def checkCompletion(self):
        for row in self.board.cells:
            for rowCell in row:
                if rowCell.id == 1:
                    return False
        return True

    def step(self, target):
        info = {}

        if self.done == True:
            return self.state, self.reward, self.done, info

        blockVal = target // 4
        block = self.board.blocks[blockVal]
        actionVal = target % 4
        action = self.actions[actionVal]

        dup = self.board.duplicateBlock(block.id, action)
        stateHash = self.board.hashCode()
        if stateHash in self.stateDict.keys():
            self.state = self.stateDict[stateHash]
        else:
            self.stateDict[stateHash] = self.stateCount
            self.state = self.stateCount
            self.stateCount += 1

        if dup:
            self.reward -= 1
            if self.checkCompletion():
                self.done = True
                info = {0: block.color, 1: action, 2: "Win"}
                self.reward += 100
            elif not self.board.hasAvailableMoves():
                self.done = True
                self.reward -= 100
                info = {0: block.color, 1: action, 2: "Loss"}
        else:
            self.invalidMove = True
            info = {block.color, action, "Invalid Move"}
            self.reward -= 10

        return self.state, self.reward, self.done, info

    def reset(self):
        self.board.reset()
        self.done = False
        self.reward = 0
        self.state = 0
        return self.state

    def render(self):
        print()
        self.board.print()
        print()

    def seed(self, seed=None):
        self.np_random, seed = seeding.np_random(seed)
        return [seed]
