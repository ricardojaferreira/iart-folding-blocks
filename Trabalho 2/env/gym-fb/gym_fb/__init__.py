from gym.envs.registration import register

register(
    id='fb-v1',
    entry_point='gym_fb.envs:FoldingBlocks',
)
