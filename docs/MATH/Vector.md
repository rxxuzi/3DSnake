# �x�N�g��

## 3��������2�����̓��e

3������Ԃ̃I�u�W�F�N�g��2�������ʏ�ɓ��e���邱�Ƃ��A3��������2�����̓��e�ƌĂԁB
���̃v���Z�X�́A�R���s���[�^�O���t�B�b�N�X�◧�̐}�`�̕`��ȂǁA���܂��܂ȃA�v���P�[�V�����Ŏg�p�����B

�ȉ��́A���3��������2�����̓��e��@�ɂ��Đ���

### ���s���e (Parallel Projection)
���s���e�́A3�����I�u�W�F�N�g�𕽍s�Ȍ������g����2�������ʂɓ��e�����@�B
���s���e�ł́A�I�u�W�F�N�g�̉��s���≓�ߊ��͍l�����ꂸ�A�����̂܂ܓ��e�����B
��ʓI�ȕ��s���e�̎�@�ɂ́A�ȉ��̂悤�Ȃ��̂�����B

+ ���ˉe (Orthographic Projection)
�F�������鎋�_����̓��e�ł��B���ׂĂ̒��������s�ɓ��e�����B
+ �Γ��e (Oblique Projection)
�F���_����̌������΂߂ɂ��邱�ƂŁA2�������ʏ�ɓ��e����B

### �������e (Perspective Projection)
�������e�́A3�����I�u�W�F�N�g�𓧎��I�Ȏ��_����2�������ʂɓ��e�����@�B
�������e�ł́A�I�u�W�F�N�g�̉��s���≓�ߊ����l������A�����ɂ���I�u�W�F�N�g�͏������Ȃ�A
�߂��ɂ���I�u�W�F�N�g�͑傫�����e�����B
�������e�ł́A���_�i�J�����j�̈ʒu�⎋��p�Ȃǂ̃p�����[�^�ɂ���ē��e���ʂ��ω�����B

�������e�ł́A�ȉ��̂悤�Ȏ�@����ʓI�Ɏg�p�����B
+ ��_�������e (One-Point Perspective)
�F���_����̌�������������1�̓_�ɏW�܂铊�e�ł���B
���ߖ@�Ƃ��֘A���Ă���A�����╽�s���������֎���������ʂ�����B
+ ��_�������e (Two-Point Perspective)
�F���_����̌�����2�̓_�ɏW�܂铊�e�B���ߖ@����苭�����A���s���̂���`�ʂ���������B
+ ���_�������e (Multi-Point Perspective)
�@�F���_����̌����������̓_�ɏW�܂铊�e�B
����̎��_�⎋��ɉ����āA��莩�R�ȓ��e���������邱�Ƃ��ł���B

## �������e�ɂ��\��

3�����x�N�g����2�����x�N�g���ŕ`�悷�邽�߂ɂ́A2�̒����x�N�g����p�ӂ���K�v������B
���̂��߂ɁA�O�����E�V���~�b�g�̐��K�������@���g�p���܂��B���̕��@�ł́A�^����ꂽ�x�N�g���̑g�𒼌����鐳�K�ȃx�N�g���̑g�ɕϊ����܂��B�ȉ��́A�O�����E�V���~�b�g�̐��K�������@�̎菇�ł��B

�^����ꂽ3�����x�N�g���̑g�� $\mathbf{v}_1, \mathbf{v}_2, \mathbf{v}_3$ �Ƃ��܂��B

1. �ŏ��̃x�N�g���𐳋K�����܂��B�ŏ��̒����x�N�g���� $\mathbf{u}_1$ �Ƃ��A���̂悤�Ɍv�Z���܂��F

$ mathbf{u}_1 = \frac{\mathbf{v}_1}{\|\mathbf{v}_1\|} $

������ $\|\mathbf{v}_1\|$ �̓x�N�g�� $\mathbf{v}_1$ �̃m�����i�����j��\���܂��B

2. 2�Ԗڂ̃x�N�g���𐳋K�����܂��B2�Ԗڂ̒����x�N�g���� $\mathbf{u}_2$ �Ƃ��A�ȉ��̎菇�Ōv�Z���܂��F

$ \[
\mathbf{u}_2 = \frac{\mathbf{v}_2 - (\mathbf{u}_1 \cdot \mathbf{v}_2)\mathbf{u}_1}{\|\mathbf{v}_2 - (\mathbf{u}_1 \cdot \mathbf{v}_2)\mathbf{u}_1\|}
\] $

�����ŁA$(\mathbf{u}_1 \cdot \mathbf{v}_2)$ �� $\mathbf{u}_1$ �� $\mathbf{v}_2$ �̓��ς�\���܂��B

3. 3�Ԗڂ̃x�N�g���𐳋K�����܂��B3�Ԗڂ̒����x�N�g���� $\mathbf{u}_3$ �Ƃ��A�ȉ��̎菇�Ōv�Z���܂��F

\[
\mathbf{u}_3 = \frac{\mathbf{v}_3 - (\mathbf{u}_1 \cdot \mathbf{v}_3)\mathbf{u}_1 - (\mathbf{u}_2 \cdot \mathbf{v}_3)\mathbf{u}_2}{\|\mathbf{v}_3 - (\mathbf{u}_1 \cdot \mathbf{v}_3)\mathbf{u}_1 - (\mathbf{u}_2 \cdot \mathbf{v}_3)\mathbf{u}_2\|}
\]

����ɂ��A�����x�N�g���̑g $\{\mathbf{u}_1, \mathbf{u}_2, \mathbf{u}_3\}$ �𓾂邱�Ƃ��ł��܂��B�����̒����x�N�g�����g�p���āA3�����x�N�g����2�����x�N�g���ŕ`�悷�邱�Ƃ��ł��܂��B

�ȏオ

�O�����E�V���~�b�g�̐��K�������@�̎菇�ł��B

�ȉ��ɁA�O�����E�V���~�b�g�̐��K�������@�̎菇��TeX�R�[�h�ŕ\�����܂��F

```tex
% �x�N�g���̑g
\mathbf{v}_1, \mathbf{v}_2, \mathbf{v}_3

% 1�Ԗڂ̒����x�N�g��
\mathbf{u}_1 = \frac{\mathbf{v}_1}{\|\mathbf{v}_1\|}

% 2�Ԗڂ̒����x�N�g��
\mathbf{u}_2 = \frac{\mathbf{v}_2 - (\mathbf{u}_1 \cdot \mathbf{v}_2)\mathbf{u}_1}{\|\mathbf{v}_2 - (\mathbf{u}_1 \cdot \mathbf{v}_2)\mathbf{u}_1\|}

% 3�Ԗڂ̒����x�N�g��
\mathbf{u}_3 = \frac{\mathbf{v}_3 - (\mathbf{u}_1 \cdot \mathbf{v}_3)\mathbf{u}_1 - (\mathbf{u}_2 \cdot \mathbf{v}_3)\mathbf{u}_2}{\|\mathbf{v}_3 - (\mathbf{u}_1 \cdot \mathbf{v}_3)\mathbf{u}_1 - (\mathbf{u}_2 \cdot \mathbf{v}_3)\mathbf{u}_2\|}
```

���̂悤�ɂ��āA�O�����E�V���~�b�g�̐��K�������@��p����3�����x�N�g����2�����x�N�g���ŕ`�悷�邱�Ƃ��ł��܂��B