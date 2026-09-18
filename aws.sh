# aws.sh
# 환경변수 문법 (터미널 안에서만 작동하는 변수)
export STUDENT_ID="student13" # 각자에 맞게
# ** 수정 플리즈 **
export AWS_PROFILE="$STUDENT_ID"
export AWS_REGION="ap-northeast-2" # 서울 리전
export AWS_PAGER=""

export MY_KEY_NAME="${STUDENT_ID}-key"
export MY_SG_NAME="${STUDENT_ID}-web-sg"
export MY_INSTANCE_NAME="${STUDENT_ID}-managed-ec2"

export VPC_ID=$(aws ec2 describe-vpcs --filters "Name=is-default,Values=true" \
  --query "Vpcs[0].VpcId" --output text)
export MY_SG_ID=$(aws ec2 describe-security-groups \
  --filters "Name=group-name,Values=$MY_SG_NAME" "Name=vpc-id,Values=$VPC_ID" \
  --query "SecurityGroups[0].GroupId" --output text)

export MY_IP=$(curl -fsS https://checkip.amazonaws.com)

export BASE_AMI_ID=$(aws ec2 describe-images \
    --owners 099720109477 \
    --filters \
      "Name=name,Values=ubuntu/images/hvm-ssd-gp3/ubuntu-noble-24.04-arm64-server-*" \
      "Name=architecture,Values=arm64" \
      "Name=state,Values=available" \
    --query "sort_by(Images,&CreationDate)[-1].ImageId" \
    --output text)

export INSTANCE_ID=$(aws ec2 describe-instances \
  --filters "Name=tag:Name,Values=$MY_INSTANCE_NAME" \
  --query "Reservations[0].Instances[0].InstanceId" --output text)

export PUBLIC_IP=$(aws ec2 describe-instances \
  --instance-ids "$INSTANCE_ID" \
  --query "Reservations[0].Instances[0].PublicIpAddress" --output text)