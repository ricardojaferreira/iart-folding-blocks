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
    [1, 1, 4, 0, 0],
    [1, 1, 4, 0, 0],
    [1, 1, 4, 0, 0],
    [1, 1, 1, 0, 0],
    [1, 1, 1, 0, 0],
    [1, 1, 1, 0, 0],
    [1, 1, 1, 1, 1],
    [2, 1, 1, 1, 5]
]

boardLayout3 = [
    [0, 2, 0, 0, 0, 0],
    [2, 1, 1, 1, 1, 0],
    [1, 1, 3, 1, 1, 0],
    [4, 1, 1, 1, 5, 0],
    [1, 1, 6, 1, 5, 5],
    [1, 1, 1, 5, 5, 5],
    [1, 7, 1, 1, 0, 0]
]


class FoldingBlocks(gym.Env):
    metadata = {'render.modes': ['human']}

    def __init__(self):
        self.actions = ["DBUP", "DBDOWN", "DBLEFT", "DBRIGHT"]

        self.boardLayout = boardLayout1
        self.board = fb_board(self.boardLayout)
        self.state = self.board.getIdBoard()
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

        blockVal = target // (self.board.blockCount + 1)
        block = self.board.blocks[blockVal]
        actionVal = target % 4
        action = self.actions[actionVal]

        info = {block.color, action}

        dup = self.board.duplicateBlock(block.id, action)
        self.state = self.board.getIdBoard()

        if dup:
            self.reward -= 1
            if self.checkCompletion():
                self.done = True
        else:
            self.invalidMove = True

        return self.state, self.reward, self.done, info

    def reset(self):
        self.board = fb_board(self.boardLayout)
        self.state = self.board.getIdBoard()
        self.done = False
        self.reward = 0
        return self.state

    def render(self):
        print()
        self.board.print()
        print()

    def seed(self, seed=None):
        self.np_random, seed = seeding.np_random(seed)
        return [seed]
