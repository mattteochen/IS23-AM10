FROM amazonlinux:2022

ARG version=17.0.6.10-1
ARG package_version=1

RUN set -eux \
    && rpm --import file:///etc/pki/rpm-gpg/RPM-GPG-KEY-amazon-linux-2022 \
    && echo "localpkg_gpgcheck=1" >> /etc/dnf/dnf.conf \
    && CORRETO_TEMP=$(mktemp -d) \
    && pushd ${CORRETO_TEMP} \
    && RPM_LIST=("java-17-amazon-corretto-headless-$version.amzn2022.${package_version}.$(uname -m).rpm" "java-17-amazon-corretto-$version.amzn2022.${package_version}.$(uname -m).rpm" "java-17-amazon-corretto-devel-$version.amzn2022.${package_version}.$(uname -m).rpm") \
    && for rpm in ${RPM_LIST[@]}; do \
    curl --fail -O https://corretto.aws/downloads/resources/$(echo $version | tr '-' '.')/${rpm} \
    && rpm -K "${CORRETO_TEMP}/${rpm}" | grep -F "${CORRETO_TEMP}/${rpm}: digests signatures OK" || exit 1; \
    done \
    && dnf install -y ${CORRETO_TEMP}/*.rpm \
    && popd \
    && rm -rf /usr/lib/jvm/java-17-amazon-corretto.$(uname -m)/lib/src.zip \
    && rm -rf ${CORRETO_TEMP} \
    && dnf clean all \
    && sed -i '/localpkg_gpgcheck=1/d' /etc/dnf/dnf.conf \
    && yum update \
    && yum install git -y \
    && yum install -y tar.x86_64 \
    && yum install maven -y

ENV LANG C.UTF-8
ENV JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto