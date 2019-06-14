package io.vickze.generator.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.vickze.common.domain.RPageRequest;
import io.vickze.generator.repository.ConfigRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Optional;
import java.util.stream.Collectors;
import io.vickze.generator.domain.DO.ConfigDO;
import io.vickze.generator.domain.DTO.ConfigQueryDTO;
import io.vickze.generator.service.ConfigService;
import java.util.ArrayList;
import javafx.util.Pair;
import org.springframework.beans.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.Arrays;
import java.util.List;

/**
 * 配置
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-03-29 16:04:44
 */
@Service
@DS("master")
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigRepository configRepository;

    @Override
    public Pair<List<ConfigDO>, Long> list(ConfigQueryDTO queryDTO) {
        Pageable pageable;
        if (queryDTO.getField() != null) {
            pageable = RPageRequest.of((int) queryDTO.getOffset(), (int) queryDTO.getLimit(),
                    Sort.Direction.valueOf(queryDTO.getOrder()), queryDTO.getField());
        } else {
            pageable = RPageRequest.of((int) queryDTO.getOffset(), (int) queryDTO.getLimit(),
                    Sort.Direction.DESC, "id");
        }

        Page<ConfigDO> page = configRepository.findAll((Specification<ConfigDO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.isNotBlank(queryDTO.getName())) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + queryDTO.getName() + "%"));
            }
            if (!CollectionUtils.isEmpty(queryDTO.getType())) {
                list.add(root.get("type").as(Integer.class).in(queryDTO.getType()));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(predicates));
        }, pageable);

        return new Pair<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public ConfigDO get(Long id) {
        return configRepository.findById(id).orElse(null);
    }

    @Override
    public void insert(ConfigDO configDO) {
        configDO.setDeleted(0);
        configRepository.save(configDO);
    }

    @Override
    public void update(ConfigDO configDO) {
        Optional<ConfigDO> configDODB = configRepository.findById(configDO.getId());
        if (configDODB.isPresent()) {
            BeanUtils.copyProperties(configDO, configDODB.get(),  "createTime", "updateTime", "deleted");
            configRepository.save(configDODB.get());
        }
    }

    @Override
    public void delete(Long... ids) {
        configRepository.deleteAll(Arrays.stream(ids).map(id -> {
            Optional<ConfigDO> configDO = configRepository.findById(id);
            return configDO.orElse(null);
        }).collect(Collectors.toList()));
    }
}
