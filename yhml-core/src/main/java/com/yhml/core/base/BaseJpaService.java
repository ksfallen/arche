// package com.yhml.core.base;
//
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;
//
// import javax.annotation.PostConstruct;
// import javax.persistence.EntityManager;
// import javax.persistence.PersistenceContext;
// import javax.persistence.Query;
// import javax.persistence.criteria.Predicate;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.util.CollectionUtils;
//
// import com.querydsl.core.BooleanBuilder;
// import com.querydsl.core.types.dsl.EntityPathBase;
// import com.querydsl.jpa.impl.JPAQueryFactory;
// import com.xinguangnet.tuchao.goodscenter.impl.repository.BaseRepository;
//
// import io.shardingjdbc.core.api.HintManager;
// import io.shardingjdbc.core.hint.HintManagerHolder;
//
//
// /**
//  * @author: Jfeng
//  * @date: 2018/6/29
//  */
//
// @org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class)
// public abstract class BaseJpaService<T extends BaseEntity> {
//
//     protected Logger log = LoggerFactory.getLogger(getClass());
//
//     protected JPAQueryFactory queryFactory;
//
//     @Autowired
//     protected BaseRepository<T> repository;
//
//     @PersistenceContext
//     protected EntityManager entityManager;
//
//     @PostConstruct
//     public void init() {
//         queryFactory = new JPAQueryFactory(entityManager);
//     }
//
//     protected abstract EntityPathBase<T> getEntityPath();
//
//     protected abstract BooleanBuilder where(BaseEntity query);
//
//     private String getEntityName() {
//         return getEntityPath().getType().getSimpleName();
//     }
//
//     public T save(T t) {
//         if (t.getId() == null) {
//             return repository.saveAndFlush(t);
//         }
//
//         // 更新
//         if (!HintManagerHolder.isDatabaseShardingOnly()) {
//             HintManager hintManager = HintManager.getInstance();
//             hintManager.setDatabaseShardingValue(String.valueOf(t.getPlatformId()));
//         }
//
//         T result = repository.saveAndFlush(t);
//         return result;
//     }
//
//     public List<T> save(List<T> list) {
//         if (CollectionUtils.isEmpty(list)) {
//             return Collections.EMPTY_LIST;
//         }
//
//         if (!HintManagerHolder.isUseShardingHint()) {
//             HintManager hintManager = HintManager.getInstance();
//             hintManager.setDatabaseShardingValue(String.valueOf(list.get(0).getPlatformId()));
//         }
//
//         return repository.save(list);
//     }
//
//     public long deleteById(Long id, Integer platformId) {
//         String sql = "update " + getEntityName() + "set delFlag=true " + " where id=" + id + " and platformId=" + platformId;
//         Query query = entityManager.createQuery(sql);
//         return query.executeUpdate();
//     }
//
//     @Transactional(readOnly = true)
//     public T findById(Long id, Integer platformId) {
//         return repository.findOne((root, query, cb) -> {
//             List<Predicate> list = new ArrayList<>();
//             list.add(cb.equal(root.get("id").as(Long.class), id));
//             list.add(cb.equal(root.get("platformId").as(Integer.class), platformId));
//             list.add(cb.equal(root.get("delFlag").as(Boolean.class), false));
//             return query.where(list.toArray(new Predicate[0])).getRestriction();
//         });
//     }
//
//     @Transactional(readOnly = true)
//     public List<T> findList(BaseEntity query) {
//         return (List<T>) repository.findAll(where(query));
//     }
//
//     @Transactional(readOnly = true)
//     public Page<T> findByPage(BaseEntity query, PageRequest page) {
//         return repository.findAll(where(query), page);
//     }
//
// }
