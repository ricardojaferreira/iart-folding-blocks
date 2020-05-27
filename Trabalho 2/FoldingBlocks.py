import gym
import gym_fb
import numpy as np
from matplotlib import pyplot as plt
import random


def plot(xLabel, yLabel, *args):
    plt.autoscale()
    for arg in args:
        plt.plot(arg)
    plt.xlabel(xLabel)
    plt.ylabel(yLabel)


def loadQTableCSV(filepath):
    return np.genfromtxt(filepath, delimiter=',')


def saveQTableCSV(qtable, filepath):
    np.savetxt(filepath, qtable, delimiter=",")


def selectAction(qtable, state, eps):
    if random.uniform(0, 1) > eps:
        return int(np.argmax(qtable[state, :]))
    return env.action_space.sample()  # take a random action


def sarsaLearning(env, qtable, eps, eps_min, eps_decay, episodes, steps, lr, gamma):
    stepStats = np.zeros(episodes)
    rewards = []
    wincount = 0

    for ep in range(episodes):
        state = env.reset()
        done = False
        epReward = 0
        action = selectAction(qtable, state, eps)

        for step in range(steps):
            state2, reward, done, info = env.step(action)
            epReward += reward

            action2 = selectAction(qtable, state2, eps)

            target = reward + gamma * qtable[state2, action2]
            delta = target - qtable[state, action]

            qtable[state, action] += lr * delta

            state = state2
            action = action2

            if done:
                stepStats[ep] = step+1
                rewards.append(epReward)
                if (info[2] == "Win"):
                    wincount += 1
                break

        if eps >= eps_min:
            eps *= eps_decay

    return stepStats, rewards, wincount


def qLearning(env, qtable, eps, eps_min, eps_decay, episodes, steps, lr, gamma):

    stepStats = np.zeros(episodes)
    rewards = []
    wincount = 0

    for ep in range(episodes):
        state = env.reset()
        done = False
        epReward = 0
        for step in range(steps):
            action = selectAction(qtable, state, eps)

            new_state, reward, done, info = env.step(action)
            epReward += reward

            old_value = qtable[state, action]
            next_max = np.max(qtable[new_state, :])

            new_value = (1 - lr) * old_value + \
                lr * (reward + gamma * next_max)

            qtable[state, action] = new_value

            state = new_state

            if done:
                stepStats[ep] = step+1
                rewards.append(epReward)
                if (info[2] == "Win"):
                    wincount += 1
                break

        if eps >= eps_min:
            eps *= eps_decay

    return stepStats, rewards, wincount


env = gym.make("fb-v1")  # Create environment
env.render()
qtable = np.zeros((2000, env.action_space.n))  # Generate new QTable
stepStatsQ, rewardStatsQ, winCountQ = qLearning(
    env, qtable, 1.0, 0.005, 0.995, 1000, 100, 0.65, 0.65)
env.close()

env = gym.make("fb-v1")  # Create environment
qtable = np.zeros((2000, env.action_space.n))  # Generate new QTable
stepStatsS, rewardStatsS, winCountS = sarsaLearning(
    env, qtable, 0.9, 0.005, 0.995, 1000, 100, 0.65, 1.0)
env.close()


#legend = {"QLearning", "Sarsa"}
#y_pos = np.arange(len(legend))
#vals = [winCountQ, winCountS]
#plt.bar(y_pos, vals, align='center', alpha=0.5)
#plt.xticks(y_pos, legend)
#plt.ylabel('Sucessful Episodes in 1000 attempts')
# plt.show()

plot("Episode Number", "Steps to completion", stepStatsQ, stepStatsS)
plt.legend(["QLearning", "Sarsa Learning"], loc="upper left")
plt.show()
