import gym

import gym_fb

env = gym.make("fb-v1")
env.reset()
env.render()
for i in range(100):
    state, reward, done, info = env.step(
        env.action_space.sample())  # take a random action
    if done:
        print("Episode finished after {} timesteps".format(i+1))
        break
    # env.render()
    print(info)
env.render()
env.close()
